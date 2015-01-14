package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.meta.internal.ast._
import scala.obey.model._
import scala.obey.model._
import scala.obey.tools.Enrichment._

@Tag("Type", "Explicit") object ExplicitUnitReturn extends Rule {

  def description = "ExplicitUnitReturn: Ensure explicit Return types"

  def message(t: Defn.Def) = Message(s"$t has no explicit Unit return type", t)

  def apply = {
    (transform {
      case t @ Defn.Def(mods, name, tparams, paramss, None, body) if t.isUnit =>
        Defn.Def(mods, name, tparams, paramss, Some(Type.Name("Unit")), body) andCollect message(t)
    }).topDown
  }
}

//Defn.Def( mods, name, tparams, paramss, decltpe, body)