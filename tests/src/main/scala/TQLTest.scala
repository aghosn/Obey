import tql._
import tqlscalameta.ScalaMetaTraverser._
import scala.meta._
import syntactic._
import tql.CombinatorsSugar._

object TQLTest {

	 def showTree(x: Tree) = show.ShowOps(x).show[syntactic.show.Raw]

  val x =
    q"""
       if (1 == 1) 1
       else 2
       5
       """

  val getAllIntLits = downBreak(collect{case Lit.Int(a) => 2})

	def main(args: Array[String]) {
		println(getAllIntLits(x))
	}
}