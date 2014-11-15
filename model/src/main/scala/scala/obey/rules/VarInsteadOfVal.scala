package scala.obey.rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.tools.Utils._

@Tag("Var") @Tag("format") object VarInsteadOfVal extends Rule {
  val name = "Var Instead of Val"

  def warning(t: Tree): Warning = Warning(s"The 'var' $t was never reassigned and should therefore be a 'val'")

  def report = {
    collectIn[Set] {
      case Term.Assign(b: Term.Name, _) => b
    }.down feed { assign =>
      collect {
        case Defn.Var(_, (b: Term.Name)::Nil, _, _) if (!assign.contains(b)) => warning(b)
      }.down
    }
  }

  def abort = ???

  def format = {
    collectIn[Set] {
      case Term.Assign(b: Term.Name, _) => b
    }.down feed { assign =>
      update {
        case Defn.Var(a, (b: Term.Name)::Nil, c, Some(d)) if (!assign.contains(b)) => 
          Defn.Val(a, b::Nil, c, d)
      }.down
    }
  }
}