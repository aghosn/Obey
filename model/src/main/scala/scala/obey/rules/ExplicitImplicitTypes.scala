package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.meta.internal.ast._
import scala.obey.model._
import scala.meta.semantic._

@Tag("Type", "Explicit", "Dotty") class ExplicitImplicitTypes(implicit c: Context) extends Rule {

  def description = "Type inference for return types of implicit vals and defs isn't supported in Dotty"

  def message(t: Tree, tpe: Type) = Message(s"result type ($tpe) of implicit definition needs to be given explicitly", t)

  def apply = transform {
    case t @ Defn.Val(mods, (name: Term.Name) :: Nil, None, rhs) if mods.exists(_.isInstanceOf[Mod.Implicit]) =>
      Defn.Val(mods, (name: Term.Name) :: Nil, Some(rhs.tpe), rhs) andCollect message(t, rhs.tpe)

    case t @ Defn.Def(mods, name, tparams, paramss, None, body) if t.isImplicit =>
      Defn.Def(mods, name, tparams, paramss, Some(body.tpe), body) andCollect message(t, body.tpe)
  }.topDown
}