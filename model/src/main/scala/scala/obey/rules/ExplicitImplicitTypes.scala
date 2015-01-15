package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.meta.internal.ast._
import scala.obey.model._
import scala.meta.semantic._
import scala.language.implicitConversions

@Tag("Type", "Explicit") class ExplicitImplicitTypes(implicit c: Context) extends Rule {


  def description = "Explicit types in implicit Val & Def"

  def message(t: Defn, tpe: scala.meta.Type) = Message(s"Implicit type $tpe in $t", t)

  implicit def toType(t: scala.meta.Type) = t.asInstanceOf[scala.meta.internal.ast.Type]
  implicit def toType2(t: scala.meta.Type): scala.meta.internal.ast.Type.Arg = t.asInstanceOf[scala.meta.internal.ast.Type.Arg]

  def apply = {
    transform {
      case t @ Defn.Val(mods, pats, None, rhs) if mods.exists(_.isInstanceOf[Mod.Implicit]) =>
        Defn.Val(mods, pats, Some(rhs.tpe), rhs) andCollect message(t, rhs.tpe)
      
      case t @ Defn.Def(mods, name, tparams, paramss, None, body) if mods.exists(_.isInstanceOf[Mod.Implicit]) =>
        Defn.Def(mods, name, tparams, paramss, Some(body.tpe), body) andCollect message(t, body.tpe)

      case tree @ Term.Param(mods, name, None, Some(t)) if mods.exists(_.isInstanceOf[Mod.Implicit]) =>
        Term.Param(mods, name, Some(toType2(t.tpe)), Some(t)) andCollect Message(s"Implicit type ${t.tpe} in $tree", tree)

    }.topDown
  }
}