import org.scalatest.FunSuite
import scala.meta.internal.ast._
import scala.meta._
import scala.obey.rules._
import scala.obey.model._
import scala.reflect.runtime.{ universe => ru }
import scala.obey.tools.Utils._
import scala.obey.tools.Enrichment._
import tqlscalameta.ScalaMetaTraverser._

class ExplicitRulesTest extends FunSuite {

  val x = 
    q"""
      val c = 3
      def pp {println("Hello")}

      def simple(i: Int) = i + 1

      def lala: Int = 3
    """
  test("Explicit Rule simple test") {
    assert(ExplicitImplicitTypes.apply(x).result.size == 3)
    assert(ExplicitUnitReturn.apply(x).result.size == 1)
  }

  test("Explicit Rule medium test") {
    assert(ExplicitUnitReturn.apply(ExplicitUnitReturn.apply(x).tree.getOrElse(x)).result.isEmpty)
    assert(ExplicitImplicitTypes.apply(ExplicitImplicitTypes.apply(x).tree.getOrElse(x)).result.isEmpty)
  }
}