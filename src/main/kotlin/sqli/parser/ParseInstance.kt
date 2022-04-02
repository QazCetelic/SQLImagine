package sqli.parser

import kotlin.system.measureTimeMillis

data class Issue(val message: String, val line: Int, val column: Int?)

class ParseInstance {
    val tables: List<Table>

    var errors = listOf<Issue>()
        internal set
    var warnings = listOf<Issue>()
        internal set
    val success: Boolean
        get() = errors.isEmpty()

    // Used for serialization
    private val tokens: List<AbstractToken>
    private val scriptIndent: String

    /**
     * How many millis it took to parse the input
     */
    val time: Long

    constructor(input: String) {
        time = measureTimeMillis {
            val lines = input
                // Trims to reduce complexity for parsing
                .trimIndent().lines()

            scriptIndent = lines.prevailingIndent()
            tokens =  lines.toTokens()

            val logicalTokens = tokens.filter { it !is DecorationToken }
            if (!verifyTokenOrder(logicalTokens)) throw IllegalStateException("Invalid token order")

            tables = logicalTokens.parseTables()
            verifyReferences(tokens, tables)
            if (errors.isNotEmpty()) throw IllegalArgumentException("Invalid script")
        }
    }

    private fun List<AbstractToken>.parseTables(): List<Table> {
        val tableTokenIndexes = mutableListOf<Int>()
        for (i in indices) {
            val token = this[i]
            if (token is TableToken) {
                tableTokenIndexes.add(i)
            }
        }
        val tableGroups = mutableListOf<Pair<TableToken, List<AttributeToken>>>()
        for (i in tableTokenIndexes.indices) {
            val currentIndex = tableTokenIndexes[i]
            val nextIndex = tableTokenIndexes.getOrNull(i + 1) ?: lastIndex
            val tableToken: TableToken = this[currentIndex] as TableToken
            val attributeTokens: List<AttributeToken> = this.subList(currentIndex + 1, nextIndex).map { it as AttributeToken }
            tableGroups += Pair(tableToken, attributeTokens)
        }
        return tableGroups.map { (tableToken, attributeTokens) ->
            val attributeMap: Map<String, Attribute> = attributeTokens.associate { token ->
                val name = token.name
                val attribute =
                    Attribute(Type.fromString(token.type), token.primaryKey, token.nullable, token.references)
                Pair(name, attribute)
            }
            Table(tableToken.name, attributeMap)
        }
    }

    /**
     * @return the script's most common indent or tab if nothing is found
     */
    private fun List<String>.prevailingIndent(): String {
        return this
            // First creates a pair so the indent only has to be trimmed once
            .map { Pair(it, it.trimIndent()) }
            // Filters out lines without indent
            .filter { it.first != it.second }
            .groupBy { it.first.removeSuffix(it.second) }
            .mapValues { it.value.size }
            .maxByOrNull { it.value }
            ?.key
            ?: "\t"
    }

    /**
     * Parses lines individually and throws an exception if an error is found after finishing
     */
    private fun List<String>.toTokens(): List<AbstractToken> {
        return this
            .mapIndexed { index, line -> processLine(line, index) }
            // map operation is split in 2 parts so the entire script is parsed before throwing exceptions
            .map { it ?: throw Exception("Invalid script") }
    }

    /**
     * Verifies that the referenced fields exist.
     */
    private fun verifyReferences(tokens: List<AbstractToken>, tables: List<Table>) {
        tokens.forEachIndexed { i, t ->
            if (t is AttributeToken) {
                for (reference in t.references) {
                    val referenceFound = tables
                        // Finds the table with the referenced name
                        .find { it.name == reference.first }
                        ?.attributes
                        // Checks if any of the attributes match the provided reference
                        ?.any { it.key == reference.second }
                        // Defaults to false if no match was found
                        ?: false

                    if (!referenceFound) {
                        log("Reference to an attribute called ${reference.second} in a table called ${reference.first} is not found", line = i, column = null, critical = true)
                    }
                }
            }
        }
    }

    /**
     * Enforces: (TableToken AttributeToken+ WhitespaceToken*)*
     */
    private fun verifyTokenOrder(tokens: List<AbstractToken>): Boolean {
        tokens.forEachIndexed { line, token ->
            when (token) {
                is TableToken -> {
                    val nextToken = tokens.getOrNull(line + 1)
                    if (nextToken !is AttributeToken) {
                        log("Table must be followed by an attribute", line = line, column = null, critical = false)
                        return false
                    }
                }
                is AttributeToken -> {
                    tokens.getOrNull(line - 1)?.let {
                        if (it is WhitespaceToken) {
                            log("Attribute must be preceded by a table", line = line, column = null, critical = false)
                            return false
                        }
                    }
                }
            }
        }
        return true
    }

    private fun log(message: String, line: Int, column: Int? = null, critical: Boolean) {
        val s = buildString {
            append(line)
            if (column != null) {
                append(":")
                append(column)
            }
            append(" [")
            append(when (critical) {
                false -> "WARNING"
                true -> "ERROR"
            })
            append("] ")
            append(message)
        }
        when (critical) {
            false -> warnings += Issue(message, line, column)
            true -> errors += Issue(message, line, column)
        }
        System.err.println(s)
    }

    private fun processLine(line: String, index: Int): AbstractToken? {
        val trimmed = line.trimStart()
        return when {
            // Comment
            trimmed.startsWith("#") -> CommentToken(
                comment = trimmed.removePrefix("#").trimStart(),
                indent = line.takeWhile(Char::isWhitespace),
            )
            // Whitespace
            line.isBlank() -> WhitespaceToken
            // Attribute
            line[0].isWhitespace() -> processAttribute(line, index)
            // Table
            else -> processTable(line, index)
        }
    }

    private fun processTable(lineText: String, line: Int): AbstractToken? {
        if (!lineText[0].isUpperCase())
            log("Table names should start with an uppercase letter", line, column = 0, critical = false)
        if (!lineText.endsWith(':'))
            log("Table declaration should end with a colon", line, column = lineText.lastIndex, critical = false)

        val invalidColumns = mutableSetOf<Int>()
        lineText.removeSuffix(":").forEachIndexed { i, c ->
            if (!c.isLetter()) invalidColumns += i
        }
        if (invalidColumns.isNotEmpty()) {
            // Logs an error for every character that isn't valid
            for (column in invalidColumns) {
                log("Table declaration can only contain letters or underscores", line, column, critical = false)
            }
            return null
        }
        return TableToken(lineText.removeSuffix(":"))
    }

    /**
     * Logs warning if indent is not consistent.
     */
    private fun indentCheck(line: String, index: Int): Boolean {
        fun String.indentEscape(): String = replace("\t", "\\t")

        val foundIndent: String = line.takeWhile(Char::isWhitespace)
        val validIndent = (scriptIndent != foundIndent)
        if (validIndent) {
            log("Indentation is not consistent, got '${foundIndent.indentEscape()}' while expecting '${scriptIndent.indentEscape()}'", index, column = null, critical = false)
        }
        return validIndent
    }

    private fun processAttribute(line: String, index: Int): AbstractToken? {
        val trimmed = line.trimStart()
        indentCheck(line, index)

        // Split references and attribute with type
        val referencesSplit = trimmed.split(" -> ")
        // Checks for the amount of arrows
        val attributeWithType: String
        val referencePart: String?
        when (referencesSplit.size) {
            // No references
            1 -> {
                attributeWithType = referencesSplit[0]
                referencePart = null
            }
            // References
            2 -> {
                attributeWithType = referencesSplit[0]
                referencePart = referencesSplit[1]
            }
            // Invalid syntax
            else -> {
                var lastIndexWithLength = 0
                for (s in referencesSplit.drop(2)) {
                    log("There are ${referencesSplit.size - 2} arrows more than allowed", line = index, column = lastIndexWithLength, critical = true)
                    lastIndexWithLength += s.length
                }
                return null
            }
        }

        val attributeWithTypeSplit = attributeWithType.split(' ')
            // Allow spacing
            .filterNot { it.isBlank() }

        // Validate attribute
        val type = when (attributeWithTypeSplit.size) {
            1 -> {
                log("No type specified, add '(TYPE)' after the attribute name", line = index, column = null, critical = false)

                "Str" // Fallback type
            }
            2 -> {
                val typeResult = processType(attributeWithTypeSplit[1], index, columnIndex = attributeWithTypeSplit[0].length + 1)
                if (typeResult.second) return null

                typeResult.first
            }
            else -> {
                log("Invalid attribute", line = index, column = null, critical = true)
                return null
            }
        }
        val nameWithProperties = attributeWithTypeSplit[0]

        val name = nameWithProperties.removePrefix("!").removeSuffix("?")
        if (Regex("^[a-z]+([_.][a-z]+)*$").matches(name)) {
            val notLowerCaseIndexes = mutableSetOf<Int>()
            name.forEachIndexed { i, c ->
                if (!(c.isLowerCase() || c == '_' || c == '.')) notLowerCaseIndexes += i
            }
            if (notLowerCaseIndexes.isNotEmpty()) {
                for (i in notLowerCaseIndexes) {
                    log("Character in '$name' should be lowercase", line = index, column = i, critical = false)
                }
            }
            val primaryKey = nameWithProperties.startsWith('!')
            val nullable = nameWithProperties.endsWith('?')

            val references = referencePart?.let {
                it.split(", ").map {
                    val p = it.split('.')
                    if (p.size != 2) {
                        log("Reference '$it' has an invalid amount of dots", line = index, column = null, critical = true)
                        return null
                    }
                    val (table, attribute) = p
                    Pair(table, attribute)
                }
            } ?: listOf()

            return AttributeToken(name, type, primaryKey, nullable, references)
        }
        else {
            log("'$name' is an invalid attribute name", line = index, column = null, critical = true)
            return null
        }
    }

    private fun processType(type: String, index: Int, columnIndex: Int): Pair<String, Boolean> {
        val trimmed = type.removeSurrounding("(", ")")

        val invalidType = trimmed !in Type
        val improperCase = !trimmed.matches(Regex("[A-Z][a-z]+"))
        val hasBrackets = type.startsWith('(') && type.endsWith(')')

        if (invalidType) {
            val types = Type.values().joinToString(separator = ", ")
            log("'$trimmed' is not a valid type ($types)", line = index, column = columnIndex, critical = true)
        }
        if (improperCase) {
            log("Attribute type should be capitalized lowercase", line = index, column = columnIndex, critical = false)
        }
        if (!hasBrackets) {
            log("Attribute type should be surrounded by brackets", line = index, column = columnIndex, critical = false)
        }

        return Pair(trimmed, invalidType)
    }

    override fun toString(): String = tokens.joinToString(separator = "\n") { it.serialize() }

    val about: String
        get() = buildString {
            val result = if (errors.isNotEmpty()) "failed to parse" else "successfully parsed in ${time}ms"
            appendLine("Script $result with ${errors.size} errors and ${warnings.size} warnings.")
            val tableCount = tables.size
            val attributeCount = tables.sumOf { it.attributes.size }
            appendLine("There are $tableCount table and $attributeCount attribute declarations.")
        }
}