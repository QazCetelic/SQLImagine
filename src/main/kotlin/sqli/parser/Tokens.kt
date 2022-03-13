package sqli.parser

internal abstract class AbstractToken {
    abstract fun serialize(): String
}

/**
 * These tokens are ignored by the lexer.
 */
internal interface DecorationToken

internal object WhitespaceToken: AbstractToken(), DecorationToken {
    override fun toString(): String = "Whitespace"
    override fun serialize(): String = ""
}

internal data class TableToken(val name: String): AbstractToken() {
    override fun serialize(): String = "$name:"
    override fun toString(): String = "Table($name)"
}

internal data class AttributeToken(
    val name: String,
    val type: String,
    val primaryKey: Boolean,
    val nullable: Boolean,
    val references: List<Pair<String, String>>,
): AbstractToken() {
    override fun serialize(): String = buildString {
        append('\t')
        if (primaryKey) append('!')
        append(name)
        if (nullable) append('?')
        append(" (")
        append(type)
        append(')')
        if (references.isNotEmpty()) {
            append(" -> ")
            append(references.joinToString(prefix = "", postfix = "", separator = ", ") { "${it.first}.${it.second}" })
        }
    }

    override fun toString(): String
        = "Attribute($name, $type, primary_key = $primaryKey, nullable = $nullable, $references)"
}

internal class CommentToken(val comment: String, indent: String): AbstractToken(), DecorationToken {
    private val indent: String
    init { this.indent = indent }
    override fun serialize(): String = "$indent# $comment"
    override fun toString(): String = "Comment"
}