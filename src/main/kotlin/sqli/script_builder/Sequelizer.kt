package sqli.script_builder

import sqli.parser.Attribute
import sqli.parser.Table
import sqli.parser.Type

/**
 * Turns parsed tables into an SQL script.
 */
class Sequelizer {

    private val indent: String
    private val database: String

    constructor(database: String, indent: String = "  ") {
        this.database = database
        if (indent.isNotBlank()) throw IllegalArgumentException("Indent must only contain whitespace")
        this.indent = indent
    }

    fun sequelize(parsed: List<Table>): String {
        return parsed.joinToString(separator = "\n") { sequelizeTable(it) }
    }

    private fun sequelizeTable(table: Table): String {
        val attributes = table.attributes.entries.map { it.toPair() }
        val dbName = "${database}.${table.name}"
        return buildString {
            append("-- Removes the old table if it exists")
            append('\n')
            append("DROP TABLE IF EXISTS $dbName;")
            append('\n')
            append("CREATE TABLE $dbName (")
            append('\n')
            append(sequelizeAttributes(attributes))
            append('\n')
            append(");")
        }
    }

    private fun sequelizeAttributes(attributes: List<Pair<String, Attribute>>): String {
        val fields = attributes.joinToString("\n") {
            sequelizeAttribute(it).prependIndent(indent) + ","
        }
        val foreignKeys = attributes.filter { it.second.references.isNotEmpty() }.joinToString("\n") {
            val (name, attribute) = it
            val reference = attribute.references.first()
            "FOREIGN KEY (${name}) REFERENCES ${reference.first}(${reference.second})".prependIndent(indent) + ","
        }
        return "$fields\n$foreignKeys".trimEnd().removeSuffix(",")
    }

    private fun sequelizeAttribute(namedAttribute: Pair<String, Attribute>): String {
        val (name, attribute) = namedAttribute
        return buildString {
            append(name)
            append(" ")
            append(sequelizeType(attribute.type))
            if (attribute.primaryKey) append(" PRIMARY KEY")
            if (attribute.nullable) append(" NULL")
        }
    }

    private fun sequelizeType(type: Type): String {
        return "${type.name}${type.size?.let { "<$it>" } ?: ""}"
    }

}