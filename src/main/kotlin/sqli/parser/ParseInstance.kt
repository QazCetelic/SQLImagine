package sqli.parser

import kotlin.system.measureTimeMillis

data class Location(val line: Int, val column: Int?)
data class Issue(val message: String, val location: Location)

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

            scriptIndent = getScriptIndent(lines)

            // Parse lines individually
            tokens =  lines
                .mapIndexed { index, line -> processLine(line, index) }
                // map operation is split in 2 parts so errors can be added to the list
                .map { it ?: throw IllegalArgumentException("Invalid script") }

            val logicalTokens = tokens.filter { it !is DecorationToken }
            if (!verifyTokenOrder(logicalTokens)) throw IllegalStateException("Invalid token order")

            val foundTables = mutableListOf<Table>()
            tokens.forEachIndexed { i, token ->
                if (token is TableToken) {
                    val attributes = buildList {
                        // Starts loop after current token
                        tokens.drop(i + 1).forEach { token2 ->
                            when (token2) {
                                is AttributeToken -> add(token2)
                                is TableToken -> return@forEach
                            }
                        }
                    }.map { Pair(it.name, Attribute(Type.fromString(it.type), it.primaryKey, it.nullable, it.references)) }

                    foundTables += Table(token.name, attributes.toMap())
                }
            }

            verifyReferences(tokens, foundTables)
            tables = foundTables
            if (errors.isNotEmpty()) throw IllegalArgumentException("Invalid script")
        }
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
                        LogType.ERROR.log("Reference to an attribute called ${reference.second} in a table called ${reference.first} is not found", line = i, column = null)
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
                        LogType.WARNING.log("Table must be followed by an attribute", line = line, column = null)
                        return false
                    }
                }
                is AttributeToken -> {
                    tokens.getOrNull(line - 1)?.let {
                        if (it is WhitespaceToken) {
                            LogType.WARNING.log("Attribute must be preceded by a table", line = line, column = null)
                            return false
                        }
                    }
                }
            }
        }
        return true
    }

    private fun getScriptIndent(lines: List<String>): String {
        // Gets the script's indent
        val mostCommonIndentOrNull: String? = lines
            // First creates a pair so the indent only has to be trimmed once
            .map { Pair(it, it.trimIndent()) }
            // Filters out lines without indent
            .filter { it.first != it.second }
            .groupBy { it.first.removeSuffix(it.second) }
            .mapValues { it.value.size }
            .maxByOrNull { it.value }
            ?.key

        return mostCommonIndentOrNull ?: "\t"
    }

    enum class LogType {
        WARNING,
        ERROR,
        INFO;
    }

    fun LogType.log(message: String, line: Int, column: Int? = null) {
        val s = buildString {
            append(line)
            if (column != null) {
                append(":")
                append(column)
            }
            append(" [")
            append(this@log)
            append("] ")
            append(message)
        }
        when (this) {
            LogType.WARNING -> warnings += Issue(message, Location(line, column))
            LogType.ERROR -> errors     += Issue(message, Location(line, column))
            else -> {}
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
            LogType.WARNING.log("Table names should start with an uppercase letter", line, column = 0)
        if (!lineText.endsWith(':'))
            LogType.WARNING.log("Table declaration should end with a colon", line, column = lineText.lastIndex)

        val invalidColumns = mutableSetOf<Int>()
        lineText.removeSuffix(":").forEachIndexed { i, c ->
            if (!c.isLetter()) invalidColumns += i
        }
        if (invalidColumns.isNotEmpty()) {
            // Logs an error for every character that isn't valid
            for (column in invalidColumns) {
                LogType.ERROR.log("Table declaration can only contain letters or underscores", line, column)
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
            LogType.WARNING.log("Indentation is not consistent, got '${foundIndent.indentEscape()}' while expecting '${scriptIndent.indentEscape()}'", index, column = null)
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
                    LogType.ERROR.log("There are ${referencesSplit.size - 2} arrows more than allowed", line = index, column = lastIndexWithLength)
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
                LogType.WARNING.log("No type specified, add '(TYPE)' after the attribute name", line = index, column = null)

                "Str" // Fallback type
            }
            2 -> {
                val typeResult = processType(attributeWithTypeSplit[1], index, columnIndex = attributeWithTypeSplit[0].length + 1)
                if (typeResult.second) return null

                typeResult.first
            }
            else -> {
                LogType.ERROR.log("Invalid attribute", line = index, column = null)
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
                    LogType.WARNING.log("Character in '$name' should be lowercase", line = index, column = i)
                }
            }
            val primaryKey = nameWithProperties.startsWith('!')
            val nullable = nameWithProperties.endsWith('?')

            val references = referencePart?.let {
                it.split(", ").map {
                    val p = it.split('.')
                    if (p.size != 2) {
                        LogType.ERROR.log("Reference '$it' has an invalid amount of dots", line = index, column = null)
                        return null
                    }
                    val (table, attribute) = p
                    Pair(table, attribute)
                }
            } ?: listOf()

            return AttributeToken(name, type, primaryKey, nullable, references)
        }
        else {
            LogType.ERROR.log("'$name' is an invalid attribute name", line = index, column = null)
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
            LogType.ERROR.log("'$trimmed' is not a valid type ($types)", line = index, column = columnIndex)
        }
        if (improperCase) {
            LogType.WARNING.log("Attribute type should be capitalized lowercase", line = index, column = columnIndex)
        }
        if (!hasBrackets) {
            LogType.WARNING.log("Attribute type should be surrounded by brackets", line = index, column = columnIndex)
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