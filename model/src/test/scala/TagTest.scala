import org.scalatest.FunSuite
import scala.meta.syntactic.ast._
import scala.obey.Rules._
import scala.obey.model._
import scala.reflect.runtime.{universe => ru}
import scala.obey.model.Tag

class TagTest extends FunSuite {

	test("Conversion from String to Tag enum") {
		assert(Tag.Var == Tag.withName("Var"))
	}
}