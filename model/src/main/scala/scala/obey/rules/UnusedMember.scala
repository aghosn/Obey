package scala.obey.rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.tools.Enrichment._
import scala.obey.tools.Utils._
import scala.language.reflectiveCalls

@Tag("DCE") @Tag("Var") object UnusedMember extends Rule {
  val name = "Unused Member"

  def message(t: Term.Name): Message = Message(s"${t} is not used")

  def ignore(d: Defn): Boolean = d.isMain && d.isValueParameter && d.isConstructorArg

  def apply = {
    collectIn[Set] {
      case t: Defn.Def if (!ignore(t)) => t.name
    }.down feed { defs =>
      collect {
        case Term.Assign(b: Term.Name, _) if (defs.contains(b)) => message(b)
      }.down
    }
  }
}

