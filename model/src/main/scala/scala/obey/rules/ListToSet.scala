package scala.obey.rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.tools.Utils._

@Tag("ReWrite") @Tag("List") object ListToSet extends Rule {
  val name: String = "List to Set"

  implicit val f = new tql.AllowedTransformation[scala.meta.syntactic.ast.Defn.Val, scala.meta.syntactic.ast.Defn.Val] {}

  def report = ???
  def abort {}
  
  def format = downBreak(
    transform[Defn.Val, Defn.Val] {
      case Defn.Val(mod, n, None, Term.Select(Term.Apply(Term.Name("List"), l), Term.Name("toSet"))) =>
        Defn.Val(mod, n, None, Term.Apply(Term.Name("Set"), l))
    })
}