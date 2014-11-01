import org.scalatest.FunSuite
import tqlscalameta._
import scala.meta._
import syntactic._

class PrintTreeTest extends FunSuite {

  def showTree(x: Tree) = show.ShowOps(x).show[syntactic.show.Raw]

  val x: Tree =
    q"""
					if (1 == 1) 1
					else 2
					5
				"""
  test("Simply printing a tree") {
    println(showTree(x));
  }

}