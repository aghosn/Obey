import org.scalatest.FunSuite
import scala.obey.utils._
import scala.obey.model.Tag

class testParser extends FunSuite {

	test("Parsing the input option") {
		val res = OptionParser.parse("(Var, Play, DCE)").get.toSet
		assert((res -- Set(Tag.Var, Tag.Play, Tag.DCE)).isEmpty)
		println(res)
	}
}

