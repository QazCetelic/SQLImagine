import org.junit.Before
import org.junit.Test
import sqli.parser.ParseInstance
import sqli.script_builder.Sequelizer
import java.io.File

class Test {
    val resources = File("${System.getProperty("user.dir")}/src/test/resources").also { println(it) }
    val cruiseScript: String = resources.resolve("Cruise.sqli").readText()
    val cruiseScriptSQL: String = resources.resolve("Cruise.sql").readText()

    lateinit var parsed: ParseInstance

    @Test
    fun parse() {
        parsed = ParseInstance(cruiseScript)
        assert(parsed.time < 75)
    }

    @Test
    fun sql() {
        val sequelizer = Sequelizer("example_db")
        val sql = sequelizer.sequelize(parsed)
        assert(sql == cruiseScriptSQL)
    }

}