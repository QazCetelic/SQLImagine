package sqli.parser

data class Attribute(
    val type: Type,
    val primaryKey: Boolean,
    val nullable: Boolean,
    val references: List<Pair<String, String>>,
)