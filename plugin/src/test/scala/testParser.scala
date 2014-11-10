import org.scalatest.FunSuite
import scala.obey.utils._
import scala.obey.model.Tag

class testParser extends FunSuite {

	test("Parsing the input option") {
		val res = OptionParser.parse("(Var, Play, DCE)")
		assert((res.map(_.tag).toSet -- Set("Var", "Play", "DCE")).isEmpty)
	}
}

