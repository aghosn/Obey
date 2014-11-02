package scala.obey.Rules

import scala.obey.model.Rule
import scala.meta._
import tqlscalameta.ScalaMetaTraverser._
import tql.Monoids._

class VarInsteadOfVal extends Rule {
  val name: String = "Var Instead of Val"

  case class Warning(tree: Tree) extends RuleWarning {
    val message: String = s"The 'var' $tree was never reassigned and should therefore be a 'val'"
  }

  val vars = down(
    collect({
      case a @ Defn.Var(_, (b: Term.Name) :: Nil, _, _) => (b, a)
    }))

  val assign = down(collect{
  	case t @ Term.Assign(b: Term.Name, _) => b
  	})

  def apply(t: Tree): List[Warning] = {
  	val res = vars(t).result.get.toSet
  	val assigns = assign(t).result.get.toSet

  	res.filter(x => !assigns.contains(x._1)).map(x => Warning(x._2)).toList
  }
}