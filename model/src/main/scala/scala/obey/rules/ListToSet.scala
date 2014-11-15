package scala.obey.rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.tools.Utils._

@Tag("format") @Tag("List") @Tag("Set") object ListToSet extends Rule {
  val name: String = "List to Set"

  def warning(t: Defn.Val): Warning = Warning(s"The assignment $t creates a useless List") 

  def apply = {
    (collect {
      case t @ Defn.Val(mod, n, None, Term.Select(Term.Apply(Term.Name("List"), l), Term.Name("toSet"))) =>
        warning(t)
    } <~
      update {
      case Defn.Val(mod, n, None, Term.Select(Term.Apply(Term.Name("List"), l), Term.Name("toSet"))) =>
        Defn.Val(mod, n, None, Term.Apply(Term.Name("Set"), l))
      }).down 
  }
}