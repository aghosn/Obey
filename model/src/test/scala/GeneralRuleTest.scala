import org.scalatest.FunSuite
import scala.meta.syntactic.ast._
import scala.meta._
import scala.obey.Rules._
import scala.obey.model._
import scala.reflect.runtime.{universe => ru}
import scala.obey.model.Tag._
import scala.obey.Tools.Enrichment._

class GeneralRuleTest extends FunSuite {

  def showTree(x: scala.meta.Tree) = scala.meta.syntactic.show.ShowOps(x).show[syntactic.show.Raw]
  val x =
    q"""
    			var a = 5
          var c = 3
          c = 5
					if (1 == 1) c = 1
					else c = 2
					
				"""
  test("Print the tree") {
    println(showTree(x))
  }


  /*This fails for the moment*/
  test("Testing VarInsteadOfVal rule") {
    val rule = VarInsteadOfVal
    //println(VarInsteadOfVal(x))
  }

  test("Annotations") {
    assert((Keeper.filterT(Set(new ATag("Var")), Set())).warners.contains(VarInsteadOfVal))
  }

}