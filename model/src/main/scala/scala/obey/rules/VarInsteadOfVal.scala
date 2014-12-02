package scala.obey.rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.tools.Utils._
import scala.language.reflectiveCalls

@Tag("Var", "Val") object VarInsteadOfVal extends Rule {
  val name = "VarInsteadOfVal: var assigned only once should be val"

  def message(t: Tree): Message = Message(s"The 'var' $t was never reassigned and should therefore be a 'val'")

  def apply: Matcher[List[Message]] = {
    collectIn[Set] {
      case Term.Assign(b: Term.Name, _) => b
    }.down feed { assign =>
      (collect {
        case Defn.Var(_, (b: Term.Name) :: Nil, _, _) if (!assign.contains(b)) => message(b)
      } <~
        update {
          case Defn.Var(a, (b: Term.Name) :: Nil, c, Some(d)) if (!assign.contains(b)) =>
            Defn.Val(a, b :: Nil, c, d)
        }).down
    }
  }
}