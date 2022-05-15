package sqli.parser

import sqli.script_builder.Sequelizer
import java.io.File

fun main() {
    val path = "/home/qaz/Programming/Kotlin/SQLImagine/src/test/resources/Cruise.sqli"
    val input = File(path).readText()

    val tables = Parser().parse(input)
    val sequelizer = Sequelizer("test");
    val sql = sequelizer.sequelize(tables)
    println(sql)
}