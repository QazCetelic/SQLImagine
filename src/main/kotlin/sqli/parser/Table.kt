package sqli.parser

data class Table(
    val name: String,
    val attributes: Map<String, Attribute>,
) {
    operator fun contains(attribute: String): Boolean {
        return attributes.containsKey(attribute)
    }

    internal val referenced: Set<String>
        get() = attributes.values.map { attribute ->
                    attribute.references.map { it.first }
                }.flatten().toSet()
}