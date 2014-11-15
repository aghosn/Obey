package scala.obey.rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.tools.Enrichment._
import scala.obey.tools.Utils._

@Tag("DCE") @Tag("Var") object UnusedMember extends Rule {
  val name = "Unused Member"

  def warning(t: Term.Name): Warning = Warning(s"${t} is not used")

  def ignore(d: Defn): Boolean = d.isMain && d.isValueParameter && d.isConstructorArg

  def report = {
    collectIn[Set] {
      case t: Defn.Def if (!ignore(t)) => t.name
    }.down feed { defs =>
      collect {
        case Term.Assign(b: Term.Name, _) if (defs.contains(b)) => warning(b)
      }.down
    }
  }

  def abort = ???
  def format = ???
}

