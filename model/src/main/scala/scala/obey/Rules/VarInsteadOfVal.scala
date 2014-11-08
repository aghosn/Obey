package scala.obey.Rules

import scala.meta._
import tqlscalameta.ScalaMetaTraverser._
import tql.Monoids._
import scala.obey.model._

@VarTag
object VarInsteadOfVal extends RuleWarning {
  val name: String = "Var Instead of Val"

  def warning(tree: Tree): Warning = Warning(s"The 'var' $tree was never reassigned and should therefore be a 'val'")  

  private val vars = down(
    collect({
      case a @ Defn.Var(_, (b: Term.Name) :: Nil, _, _) => (b, a)
    }))

  private val assign = down(collect{
  	case t @ Term.Assign(b: Term.Name, _) => b
  	})

  def apply(t: Tree): List[Warning] = {
  	val res = vars(t).result.get.toSet
  	val assigns = assign(t).result.get.toSet

  	res.filter(x => !assigns.contains(x._1)).map(x => warning(x._2)).toList
  }
}