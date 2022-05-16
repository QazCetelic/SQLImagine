package sqli.parser

import GrammarLexer
import GrammarParser
import GrammarParser.Table_declareContext
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class Parser {
    companion object {
        /**
         * - STR:   VARCHAR<64>
         * - INT:   INTEGER
         * - DEC:   FLOAT
         * - BOOL:  TINYINT<1>
         * - DATE:  DATE
         */
        val DEFAULT_ALIASES = mapOf(
            "STR" to "VARCHAR<64>",
            "INT" to "INTEGER",
            "DEC" to "FLOAT",
            "BOOL" to "TINYINT<1>",
            "DATE" to "DATE",
        )
    }
    
    val aliases: Map<String, String>

    /**
     * @param aliases - map of aliases
     * @see [Parser.DEFAULT_ALIASES]
     */
    constructor(aliases: Map<String, String> = DEFAULT_ALIASES) {
        this.aliases = aliases.mapKeys { it.key.uppercase() }
    }
    
    fun parse(input: String): List<Table> {
        val cleaned = input.replace("\r", "")
        val lexer = GrammarLexer(CharStreams.fromString(cleaned))
        val parser = GrammarParser(CommonTokenStream(lexer))
        val tableDeclarations = parser.main().table_declare()
        val tables = tableDeclarations.map { table(it) }
        verifyReferences(tables)
        return tables
    }

    private fun table(tableDeclare: Table_declareContext): Table {
        val tableName = tableDeclare.TABLE_NAME().text
        val attributeDeclarations = tableDeclare.attribute_declare()
        val attributes = attributeDeclarations.associate { attribute(it) }
        return Table(tableName, attributes)
    }

    private fun attribute(attributeDeclare: GrammarParser.Attribute_declareContext): Pair<String, Attribute> {
        val name = attributeDeclare.ATTRIBUTE_NAME().text
        val primaryKey = attributeDeclare.PRIMARY_KEY_OPERATOR() != null
        val typeDeclare = attributeDeclare.type_declare()
        val nullable = typeDeclare.NULLABLE() != null
        val type = typeDeclare.type().text
        val referenceDeclarations = attributeDeclare.reference_declare()?.REFERENCED_ATTRIBUTE_NAME()
        val references = (referenceDeclarations ?: emptyList()).map { it.text }
        val attribute = Attribute(
            Type(this, type),
            primaryKey,
            nullable,
            references.map { it.split('.').let { Pair(it[0], it[1]) } },
        )
        return Pair(name, attribute)
    }

    private fun verifyReferences(tables: List<Table>) {
        var invalid = false
        for (table in tables) {
            val otherTables = (tables - table)
            for (reference in table.referenced) {
                if (otherTables.none { otherTable -> reference == otherTable.name }) {
                    invalid = true
                    System.err.println("Invalid reference: $reference")
                }
            }
        }
        if (invalid) throw Exception("Invalid references")
    }
}