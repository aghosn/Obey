package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.meta.internal.ast._
import scala.obey.model._
import scala.meta.semantic._
/*
@Tag("Type", "Explicit") class ExplicitImplicitTypes(implicit c: Context) extends Rule {


  def description = "Explicit types in implicit Val & Def"

  def message(t: Defn, tpe: Type.Name) = Message(s"Implicit type $tpe in $t", t)

  def apply = {
    (transform {
      case t @ Defn.Val(mods, pats, None, rhs) if mods.exists(_.isInstanceOf[Mod.Implicit]) =>
        Defn.Val(mods, pats, Some(rhs.tpe), rhs) andCollect message(t, rhs.tpe)
      case t @ Defn.Def(mods, name, tparams, paramss, None, body) if mods.exists(_.isInstanceOf[Mod.Implicit]) =>
        Defn.Def(mods, name, tparams, paramss, Some(body.tpe), body) andCollect message(t, body.tpe)
    }).topDown
  }
}*/