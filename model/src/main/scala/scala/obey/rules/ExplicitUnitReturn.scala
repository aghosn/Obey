package scala.obey.rules

import tqlscalameta.ScalaMetaTraverser._

import scala.meta.internal.ast._
import scala.obey.model._
import scala.obey.model.utils._
import scala.obey.tools.Enrichment._

@Tag("Type", "Explicit") object ExplicitUnitReturn extends Rule {

  val description = "ExplicitUnitReturn: Ensure explicit Return types"

  def message(t: Defn.Def) = Message(s"$t has no explicit Unit return type", t)

  def apply = {
    (collect {
      case t @ Defn.Def(_, _, _, _, None, _) if t.isUnit =>
        message(t)
    } <~
      update {
        /*TODO does this works ? Wondering how Type.Name is compared*/
        case t @ Defn.Def(mods, name, tparams, paramss, None, body) if t.isUnit =>
          Defn.Def(mods, name, tparams, paramss, Some(Type.Name("Unit")), body)
      }).down
  }
}

//Defn.Def( mods, name, tparams, paramss, decltpe, body)