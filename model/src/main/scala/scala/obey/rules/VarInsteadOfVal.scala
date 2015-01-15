package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.language.reflectiveCalls
import scala.meta.internal.ast._
import scala.obey.model._

//TODO: test if the var is public, maybe shouldn't modify it if so.
@Tag("Var", "Val") object VarInsteadOfVal extends Rule {
  def description = "var assigned only once should be val"

  def message(n: Tree, t: Tree): Message = Message(s"The 'var' $n from ${t} was never reassigned and should therefore be a 'val'", t)

  def allAssigns = collect[Set] { case Term.Assign(b: Term.Name, _) => b }.topDown

  def rewriteUnassignedVars(s: Set[Term.Name]) = transform {
    case t @ Defn.Var(a, (b: Term.Name) :: Nil, c, Some(d)) if !s.contains(b) =>
      Defn.Val(a, b :: Nil, c, d) andCollect message(b, t)
  }.topDown

  def apply = allAssigns feed rewriteUnassignedVars
}