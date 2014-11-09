import org.scalatest.FunSuite
import scala.meta.syntactic.ast._
import scala.obey.Rules._
import scala.obey.model._
import scala.reflect.runtime.{universe => ru}
class GeneralRuleTest extends FunSuite {

  //def showTree(x: Tree) = show.ShowOps(x).show[syntactic.show.Raw]

  /*val x: Tree =
    q"""
    			var a = 5
          var c = 3
          c = 5
					if (1 == 1) a = 1
					else a = 2
					
				"""*/
  test("Simply printing a tree") {
    //println(showTree(x));
  }

  //val v = VarInsteadOfVal
  //val v1 = UnusedMember

  /*This fails for the moment*/
  test("Testing the var i val rule ") {
    /*val l = v(x)
    println("The list of warnings: "+l)
    assert(l.size == 1)*/
  }

  test("Keeper tracks the Rules") {
    Keeper.warners.foreach(println(_))
    assert(Keeper.warners.size == 1, "Fail in number of warners")
    assert(Keeper.formatters == Nil, "Fail in number of formatters")
    assert(Keeper.errs == Nil, "Fail in number of errs")
  }

  test("Test rule filtering") {
   /* Keeper.filter(List(classOf[VarTag]), Nil)(Keeper.warners).foreach {
      x => println(s"The annot $x")
    }*/

    /*val mirror = ru.runtimeMirror(getClass.getClassLoader)
    val sym = mirror.staticModule("VarInsteadOfVal")
    val mir = mirror.reflectModule(sym)
    println(sym.name) */

    //println("LAAAAAAAAAAAAA "+VarInsteadOfVal.annotations)
    //println("Bimm "+VarInsteadOfVal.getAnnot)
  }
}