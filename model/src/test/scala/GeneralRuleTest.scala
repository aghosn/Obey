import org.scalatest.FunSuite
import scala.meta.syntactic.ast._
import scala.meta._
import scala.obey.rules._
import scala.obey.model._
import scala.reflect.runtime.{ universe => ru }
import scala.obey.model.Tag
import scala.obey.tools.Enrichment._

class GeneralRuleTest extends FunSuite {

  def showTree(x: scala.meta.Tree) = scala.meta.syntactic.show.ShowOps(x).show[syntactic.show.Raw]
  val x =
    q"""
    		val a = List(1,2,3,4).toSet
					
				"""
  val y =
    q"""
      val l = Set(1,2,3,4)
      """
  test("Print the tree") {
    println(showTree(x))
    println("\n" + showTree(y))
  }

  /*This fails for the moment*/
  test("Testing VarInsteadOfVal rule") {
    val rule = VarInsteadOfVal
    println(VarInsteadOfVal(x))
  }

  test("Annotations") {
    assert((Keeper.filterT(Set(new Tag("Var")), Set())).warners.contains(VarInsteadOfVal))
  }

}