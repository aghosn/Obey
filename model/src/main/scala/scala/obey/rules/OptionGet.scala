package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.language.reflectiveCalls
import scala.meta.internal.ast._
import scala.obey.model._
import scala.obey.model._
import scala.obey.tools.Enrichment._

@Tag("Option", "Option#get") object OptionGet extends Rule {

  def description = "use Option#fold instead of get"

  def message(t: Term.Select) = Message(s"${t} should use Option#fold instead of Option#get", t)

  /*TODO Implement this*/
  def isOption(t: Type.Name): Boolean = true

  def apply = {
    collect[Set] {
      /*TODO Maybe check decltype then rhs or t*/
      case t @ Defn.Val(_, List(n @ Term.Name(v)), _, rhs) if isOption(t.getType) => n
    }.topDown feed { options =>
      (collect {
        case t @ Term.Select(n @ Term.Name(v), Term.Name("get")) if options.contains(n) =>
          message(t)
      }).topDown
    }
  }

}