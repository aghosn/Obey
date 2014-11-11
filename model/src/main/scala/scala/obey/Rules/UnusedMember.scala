package scala.obey.Rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.Tools.Enrichment._
import scala.obey.Tools.Wrapper._

object UnusedMember extends Rule {
  val name = "Unused Member"

  def warning(t: Term.Name): Warning = Warning(s"${t} is not used")

  def ignore(d: Defn): Boolean = d.isMain && d.isValueParameter && d.isConstructorArg
  
  //Need to know if it is Main, + Difference between Decl and Defn
  private val collectDef = down(
    collect({
      case t: Defn.Def if (!ignore(t)) => t.getName
    }))
	 
  def report(t: Tree): List[Warning] = {
    val defs = collectDef(t).result.toSet

    val bads = down(
      collect({
        case Term.Assign(b: Term.Name, _) if (defs.contains(b)) => b
      }))

    bads(t).result.map(x => warning(x)).toList
  }

  def abort(t: Tree){}
  def format(t: Tree){}
}

