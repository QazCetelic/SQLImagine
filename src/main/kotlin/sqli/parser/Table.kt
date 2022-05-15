package sqli.parser

data class Table(
    val name: String,
    val attributes: Map<String, Attribute>,
) {
    operator fun contains(attribute: String): Boolean {
        return attributes.containsKey(attribute)
    }

    internal val referenced: Set<String>
        get() {
            val referenced = mutableSetOf<String>()
            attributes.values.forEach {
                referenced.addAll(it.references.map { it.first })
            }
            return referenced
        }
}