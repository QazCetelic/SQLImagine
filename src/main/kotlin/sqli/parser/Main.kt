package sqli.parser

import GrammarLexer
import GrammarParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.*
import java.io.File

fun main() {
    val path = "/home/qaz/Programming/Kotlin/SQLImagine/src/test/resources/Cruise.sqli"
    val input = File(path).readText()

    val tables = Parser.parse(input)
    for (table in tables) {
        println(table.name)
        for (attribute in table.attributes) {
            println("\t${attribute}")
        }
    }
}