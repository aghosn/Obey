package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.meta.internal.ast._
import scala.obey.model._
import scala.meta.semantic._

@Tag("Type", "Explicit", "Dotty") class ExplicitUnitReturn(implicit c: Context) extends Rule {

  def description = "Procedure syntax isn't supported in Dotty"

  def message(t: Defn.Procedure) = Message(s"$t uses unsupported procedure syntax", t)

  def apply = {
    /*transform {
      case t @ Defn.Def(mods, name, tparams, paramss, _, body) if body.tpe == typeOf[Unit] =>
        Defn.Def(mods, name, tparams, paramss, Some(typeOf[Unit]), body) andCollect message(t)
    }.topDown*/
    transform {
      case t @ Defn.Procedure(mods, name, tparams, paramss, stats) =>
        Defn.Def(mods, name, tparams, paramss, Some(typeOf[Unit]), Term.Block(t.stats)) andCollect message(t)
    }.topDown
  }
}