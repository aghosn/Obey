import org.scalatest.FunSuite
import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.meta._
import scala.obey.model._
import scala.obey.model.utils._
import scala.obey.rules._
import scala.reflect.runtime.{universe => ru}

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
  val z =
    q"""
      List(1,2,3).toSet
    """
  val t =
    q"""
      List(1,2,3).toSet()
    """
  val a =
    q"""
      def boom(loop: List[Int]): Unit = println("coucou")
    """

  val b =
    q"""
      def boom(loop: List[Int]) = println("coucou")
    """
  val c =
    q"""
      val c: Option[String] = Some("bla")
      c.get
    """
  val treeComplete =
    q"""

  object Propaganda {

    def main(args: Array[String]) {
      println("Starting the Propaganda!")

      var c = 3
      val v = List(1,2,3).toSet
      val h = new Hanoi(new Tower(0), new Tower(1), new Tower(2))
      h.init
      println("Initial "+h)
      h.solve
      println("Result "+h)
    }
  }"""
  test("Print the tree") {
    println("The trees \n" + showTree(a) + "\n" + showTree(b))
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
    assert(Keeper.filterT(Set(), Set(new Tag("Set"))).size == Keeper.rules.size - 1)
    assert(!Keeper.filterT(Set(), Set(new Tag("Var"))).contains(VarInsteadOfVal));
  }

  test("Want to see what an option looks like") {
    println("----------------------")
    println(showTree(c))
  }

  test("More complicated tree") {
    val res = VarInsteadOfVal.apply(treeComplete).result
    assert(res.size == 1)
    val res2 = ListToSet.apply(treeComplete).result
    assert(res2.size == 1)
    println(s"${res}, ${res2}")
  }
}