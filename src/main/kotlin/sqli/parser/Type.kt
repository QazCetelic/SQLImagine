package sqli.parser

class Type {
    val name: String
    val size: Int?
    constructor(name: String, size: Int?) {
        this.name = name
        this.size = size
    }

    constructor(s: String) {
        val sizeOpen = s.indexOf('<')
        val sizeClose = s.indexOf('>')
        if (sizeOpen == -1 /* && sizeClose == -1, implied*/) {
            name = s
            size = null
        }
        else {
            name = s.substring(0, sizeOpen).uppercase()
            size = s.substring(sizeOpen, sizeClose).toInt()
        }
    }
}