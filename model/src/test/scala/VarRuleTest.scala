import org.scalatest.FunSuite
import tqlscalameta.ScalaMetaTraverser._

import scala.meta._
import scala.obey.rules._
import scala.reflect.runtime.{universe => ru}

class VarRuleTest extends FunSuite {
  def showTree(x: scala.meta.Tree) = scala.meta.syntactic.show.ShowOps(x).show[syntactic.show.Raw]

  val x = 
    q"""
      var c = 3
      if(c < 2)
        println("coucou")
      else 
        println("Hello")
    """

  val y = 
    q"""
      var c = 4
      val a = 5

      def coucou(l: Int): Int = {
        var c = 7
        c
      }

      c = 9
    """
  test("Var instead of Val simple") {
    assert(! (VarInsteadOfVal.apply(x).result).isEmpty)
  }

  test("Var instead of Val medium") {
    assert((VarInsteadOfVal.apply(y).result).size == 1)
    assert(VarInsteadOfVal.apply(VarInsteadOfVal.apply(y).tree.getOrElse(y)).result.isEmpty)
  }
}