package scala.obey.rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.tools.Utils._

@Tag("format") @Tag("List") @Tag("Set") object ListToSet extends Rule {
  val name: String = "List to Set"

  def report = ???
  def abort = ???
  
  def format = downBreak(
    update {
      case Defn.Val(mod, n, None, Term.Select(Term.Apply(Term.Name("List"), l), Term.Name("toSet"))) =>
        Defn.Val(mod, n, None, Term.Apply(Term.Name("Set"), l))
    })
}