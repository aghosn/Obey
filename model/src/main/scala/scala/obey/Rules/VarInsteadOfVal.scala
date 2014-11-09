package scala.obey.Rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.reflect.runtime.{universe => ru}
import scala.obey.model.Tag._

object VarInsteadOfVal extends RuleWarning {
  val name: String = "Var Instead of Val"
  override val tags: Set[Tag] = Set(Var)

  def warning(t: Tree): Warning = Warning(s"The 'var' $t was never reassigned and should therefore be a 'val'")  

  private val vars = down(
    collect({
      case a @ Defn.Var(_, (b: Term.Name) :: Nil, _, _) => (b, a)
    }))

  private val assign = down(collect{
  	case t @ Term.Assign(b: Term.Name, _) => b
  	})

  def apply(t: Tree): List[Warning] = {
  	val res = vars(t).result.toSet
  	val assigns = assign(t).result.toSet

  	res.filter(x => !assigns.contains(x._1)).map(x => warning(x._2)).toList
  }

}