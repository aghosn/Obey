import org.scalatest.FunSuite
import scala.meta.syntactic.ast._
import scala.meta._
import scala.obey.rules._
import scala.obey.model._
import scala.reflect.runtime.{ universe => ru }
import scala.obey.tools.Utils._
import scala.obey.tools.Enrichment._
import tqlscalameta.ScalaMetaTraverser._


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
    //println(showTree(x))
    //println("\n" + showTree(y))
  }

  /*This fails for the moment*/
  test("Testing VarInsteadOfVal rule") {
    val rule = VarInsteadOfVal
    println(VarInsteadOfVal.apply(x).result)
  }

  test("Annotations only positive filtering") {
    assert((Keeper.filterT(Set(new Tag("Var")), Set())).contains(VarInsteadOfVal))
    assert((Keeper.filterT(Set(new Tag("format")), Set())).size == 2)
    assert((Keeper.filterT(Set(new Tag("Set")), Set())).size == 1)
    assert((Keeper.filterT(Set(), Set())).size == Keeper.rules.size)
  }

  test("Annotations only negative filtering") {
    assert(Keeper.filterT(Set(), Set(new Tag("Set"))).size == Keeper.rules.size -1)
    assert(!Keeper.filterT(Set(), Set(new Tag("Var"))).contains(VarInsteadOfVal));
  }

}