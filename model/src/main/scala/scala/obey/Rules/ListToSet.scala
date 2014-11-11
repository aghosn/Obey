package scala.obey.Rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._

object ListToSet extends RuleFormat {
	val name: String = "List to Set"

	implicit val f = new tql.AllowedTransformation[scala.meta.syntactic.ast.Defn.Val, scala.meta.syntactic.ast.Defn.Val]{}
	val find = downBreak(
   transform[Defn.Val, Defn.Val]{
     case  Defn.Val(mod, n, None, Term.Select(Term.Apply(Term.Name("List"), l), Term.Name("toSet")))=>
       Defn.Val(mod, n, None, Term.Apply(Term.Name("Set"), l))
   })
	def apply(t: Tree) = {
		
	}
}