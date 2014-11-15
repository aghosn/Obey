import org.scalatest.FunSuite
import scala.obey.utils._
import scala.obey.tools.Utils._

class testParser extends FunSuite {

	test("Parsing the input option") {
		val res = OptionParser.parse("(Var, Play, DCE)")
		assert((res.map(_.tag).toSet -- Set("Var", "Play", "DCE")).isEmpty)
	}

  test("Parsing input with OptParser") {
    val res = OptParser.parse("+Var+Play-DCE")
    assert(res._1.size == 2 && res._2.size == 1)
    assert(res._1.contains(Tag("Var")))
    assert(res._2.contains(Tag("DCE")))
  }
}

