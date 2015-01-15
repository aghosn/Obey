package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.meta.internal.ast._
import scala.obey.model._
import scala.meta.semantic._

/*@Tag("Type", "Explicit") class ExplicitUnitReturn(implicit c: Context) extends Rule {

  def description = "ExplicitUnitReturn: Ensure explicit Return types"

  def message(t: Defn.Def) = Message(s"$t has no explicit Unit return type", t)

  def apply = {
    (transform {
      case t @ Defn.Def(mods, name, tparams, paramss, None, body) if body.tpe == typeOf[Unit] =>
        Defn.Def(mods, name, tparams, paramss, Some(typeOf[Unit]), body) andCollect message(t)
    }).topDown
  }
}*/