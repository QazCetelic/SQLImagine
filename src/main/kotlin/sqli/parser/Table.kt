package sqli.parser

data class Table(
    val name: String,
    val attributes: Map<String, Attribute>,
) {
    operator fun contains(attribute: String): Boolean =
        attributes.containsKey(attribute)
}

data class Attribute(
    val type: Type,
    val primaryKey: Boolean,
    val nullable: Boolean,
    val references: List<Pair<String, String>>,
)