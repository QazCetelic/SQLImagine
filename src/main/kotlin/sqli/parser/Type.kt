package sqli.parser

class Type {
    val name: String
    val size: Int?
    internal constructor(name: String, size: Int?) {
        this.name = name
        this.size = size
    }

    internal constructor(parser: Parser, s: String) {
        val aliases = parser.aliases
        val s2 = aliases.getOrDefault(s.uppercase(), s)
        val sizeOpen = s2.indexOf('<')
        val sizeClose = s2.indexOf('>')
        if (sizeOpen == -1 /* && sizeClose == -1, implied*/) {
            name = s2
            size = null
        }
        else {
            name = s2.substring(0, sizeOpen).uppercase()
            size = s2.substring(sizeOpen, sizeClose).drop(1).toInt()
        }
    }
}