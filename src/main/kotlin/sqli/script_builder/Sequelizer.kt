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
    private val databasePrefix: String
        get() = if (database.isBlank()) "" else "$database."

    constructor(database: String, indent: String = "  ") {
        this.database = database
        if (indent.isNotBlank()) throw IllegalArgumentException("Indent must only contain whitespace")
        this.indent = indent
    }

    fun sequelize(parsed: List<Table>): String {
        return listOf(
            "SET FOREIGN_KEY_CHECKS=0;",
            dropAll(parsed),
            parsed.joinToString(separator = "\n") { table(it) },
            "SET FOREIGN_KEY_CHECKS=1;"
        ).joinToString(separator = "\n")
    }

    private fun dropAll(tables: List<Table>): String {
        return listOf(
            "-- Removes the old ${tables.joinToString(separator = ", ") { it.name }} tables if they exists",
            tables.joinToString(separator = "\n") { table ->
                "DROP TABLE IF EXISTS ${databasePrefix}${table.name};"
            }
        ).joinToString(separator = "\n")
    }

    private fun table(table: Table): String {
        val attributes = table.attributes.entries.map { it.toPair() }
        val dbName = "${databasePrefix}${table.name}"
        return listOf(
            "CREATE TABLE $dbName (",
            attributes(attributes),
            ");"
        ).joinToString(separator = "\n")
    }

    private fun attributes(attributes: List<Pair<String, Attribute>>): String {
        val fields = attributes.joinToString(",\n") {
            attribute(it).prependIndent(indent)
        }
        val primaryKeys = ("PRIMARY KEY" + attributes
            .filter { it.second.primaryKey }
            .joinToString(separator = ", ", prefix = "(", postfix = ")") { escape(it.first) }).prependIndent(indent)
        val foreignKeys = attributes
            .filter { it.second.references.isNotEmpty() }
            .joinToString(",\n") {
                val (name, attribute) = it
                val reference = attribute.references.first()
                "FOREIGN KEY (${escape(name)}) REFERENCES ${reference.first}(${escape(reference.second)})".prependIndent(indent)
            }
        return listOf(fields, primaryKeys, foreignKeys).filter { it.isNotBlank() }.joinToString(separator = ",\n")
    }

    private fun attribute(namedAttribute: Pair<String, Attribute>): String {
        val (name, attribute) = namedAttribute
        return buildString {
            append(escape(name))
            append(" ")
            append(type(attribute.type))
            if (attribute.nullable) append(" NULL")
        }
    }

    private fun escape(s: String) = "`$s`"

    private fun type(type: Type): String {
        return "${type.name}${type.size?.let { "($it)" } ?: ""}"
    }

}