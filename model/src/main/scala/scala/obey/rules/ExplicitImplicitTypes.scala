package scala.obey.rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.tools.Utils._
import scala.obey.tools.Enrichment._

@Tag("Type", "Explicit") object ExplicitImplicitTypes extends Rule {

  val name = "ExplicitImplicitTypes: Explicit types in Val & Def"

  def message(t: Defn, tpe: Type.Name) = Message(s"Implicit type $tpe in $t")

  def apply = {
    (collect {
      case t @ Defn.Val(_, _, None, _) =>
        message(t, t.getType)
      case t @ Defn.Def(_, _, _, _, None, _) =>
        message(t, t.getType)
    } <~
      update {
        case t @ Defn.Val(mods, pats, None, rhs) =>
          Defn.Val(mods, pats, Some(t.getType), rhs)
      } +
      update {
        case t @ Defn.Def(mods, name, tparams, paramss, None, body) =>
          Defn.Def(mods, name, tparams, paramss, Some(t.getType), body)
      }).down
  }
}