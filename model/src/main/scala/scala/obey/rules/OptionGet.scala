package scala.obey.rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.tools.Utils._
import scala.obey.tools.Enrichment._
import scala.language.reflectiveCalls

object OptionGet extends Rule {

  val name = "Option#fold instead of get"

  def message(t: Term.Select) = Message(s"${t} should use Option#fold instead of Option#get")

  /*TODO Implement this*/
  def isOption(t: Type.Name): Boolean = true

  def apply = {
    collectIn[Set] {
      /*TODO Maybe check decltype then rhs or t*/
      case t @ Defn.Val(_, List(n @ Term.Name(v)), _, rhs) if isOption(t.getType) => n
    }.down feed { options =>
      (collect {
        case t @ Term.Select(n @ Term.Name(v), Term.Name("get")) if options.contains(n) =>
          message(t)
      }).down
    }
  }
}