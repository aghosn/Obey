package scala.obey.rules

import tqlscalameta.ScalaMetaTraverser._

import scala.language.reflectiveCalls
import scala.meta.internal.ast._
import scala.obey.model._
import scala.obey.model.utils._

@Tag("Var", "Val") object VarInsteadOfVal extends Rule {
  val name = "VarInsteadOfVal: var assigned only once should be val"

  def message(n: Tree, t: Tree): Message = Message(s"The 'var' $n from ${t} was never reassigned and should therefore be a 'val'", t)

  def apply: Matcher[List[Message]] = {
    collectIn[Set] {
      case Term.Assign(b: Term.Name, _) => b
    }.down feed { assign =>
      (collect {
        case t @ Defn.Var(_, (b: Term.Name) :: Nil, _, _) if (!assign.contains(b)) => message(b, t)
      } <~
        update {
          case Defn.Var(a, (b: Term.Name) :: Nil, c, Some(d)) if (!assign.contains(b)) =>
            Defn.Val(a, b :: Nil, c, d)
        }).down
    }
  }
}