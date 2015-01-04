package scala.obey.rules

import scala.meta.tql.ScalaMetaTraverser._

import scala.meta.internal.ast._
import scala.obey.model._
import scala.obey.model.utils._
import scala.obey.tools.Enrichment._

@Tag("Type", "Explicit") object ExplicitUnitReturn extends Rule {

  val description = "ExplicitUnitReturn: Ensure explicit Return types"

  def message(t: Defn.Def) = Message(s"$t has no explicit Unit return type", t)

  def apply = {
    (transform {
      case t @ Defn.Def(mods, name, tparams, paramss, None, body) if t.isUnit =>
        Defn.Def(mods, name, tparams, paramss, Some(Type.Name("Unit")), body) andCollect message(t)
    }).down
  }
}

//Defn.Def( mods, name, tparams, paramss, decltpe, body)