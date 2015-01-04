package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.language.reflectiveCalls
import scala.meta.internal.ast._
import scala.obey.model._
import scala.obey.model.utils._

@Tag("Var", "Val") object VarInsteadOfVal extends Rule {
  val description = "var assigned only once should be val"

  def message(n: Tree, t: Tree): Message = Message(s"The 'var' $n from ${t} was never reassigned and should therefore be a 'val'", t)

  def apply = {
    collect[Set] {
      case Term.Assign(b: Term.Name, _) => b
    }.down feed { assign =>
      (transform {
        case t @ Defn.Var(a, (b: Term.Name) :: Nil, c, Some(d)) if (!assign.contains(b)) =>
          Defn.Val(a, b :: Nil, c, d) andCollect message(b, t)
      }).down
    }
  }
}