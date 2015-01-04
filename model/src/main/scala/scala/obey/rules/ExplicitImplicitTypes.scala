package scala.obey.rules

import scala.meta.tql.ScalaMetaTraverser._

import scala.meta.internal.ast._
import scala.obey.model._
import scala.obey.model.utils._
import scala.obey.tools.Enrichment._

@Tag("Type", "Explicit") object ExplicitImplicitTypes extends Rule {

  val description = "ExplicitImplicitTypes: Explicit types in Val & Def"

  def message(t: Defn, tpe: Type.Name) = Message(s"Implicit type $tpe in $t", t)

  def apply = {
    (transform {
      case t @ Defn.Val(mods, pats, None, rhs) =>
        Defn.Val(mods, pats, Some(t.getType), rhs) andCollect message(t, t.getType)
      case t @ Defn.Def(mods, name, tparams, paramss, None, body) =>
        Defn.Def(mods, name, tparams, paramss, Some(t.getType), body) andCollect message(t, t.getType)
    }).down
  }
}