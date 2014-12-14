import org.scalatest.FunSuite

import scala.meta._
import scala.meta.syntactic._

class PrintTreeTest extends FunSuite {

  def showTree(x: Tree) = show.ShowOps(x).show[syntactic.show.Raw]

  val x: Tree =
    q"""
    			var a = 5
					if (1 == 1) a = 1
					else a = 2
					
				"""
  test("Simply printing a tree") {
    println(showTree(x));
  }

}