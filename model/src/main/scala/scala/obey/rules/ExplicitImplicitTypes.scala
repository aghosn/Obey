package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.meta.internal.ast._
import scala.obey.model._
import scala.meta.semantic._

/*@Tag("Type", "Explicit") class ExplicitImplicitTypes(implicit c: Context) extends Rule {


  def description = "Explicit types in implicit Val & Def"

  def message(t: Defn, tpe: Type.Name) = Message(s"Implicit type $tpe in $t", t)

  def toExplicit = transform {
      case tree @ Term.Param(mods, name, None, Some(t)) if mods.exists(_.isInstanceOf[Mod.Implicit]) => 
        Term.Param(mods, name, Some(t.tpe), Some(t)) andCollect Message(s"Implicit type ${t.tpe} in $tree", tree)
    }.topDown


  def apply = {
    (transform {
      case t @ Defn.Val(mods, pats, None, rhs) if mods.exists(_.isInstanceOf[Mod.Implicit]) =>
        Defn.Val(mods, pats, Some(rhs.tpe), rhs) andCollect message(t, rhs.tpe)
      
      case t @ Defn.Def(mods, name, tparams, paramss, None, body) if mods.exists(_.isInstanceOf[Mod.Implicit]) =>
        Defn.Def(mods, name, tparams, paramss, Some(body.tpe), body) andCollect message(t, body.tpe)

      case Ctor.Primary(mods, paramss) => 
        val res = toExplicit(paramss)
        Ctor.Primary(mods, res.tree.get) andCollect res.result

      case Ctor.Secondary(mods, paramss, primaryCtor, stats) => 
        val res1 = toExplicit(paramss)
        val res2 = toExplicit(primaryCtor)
        Ctor.Secondary(mods, res1.tree.get, res2.tree.get, stats) andCollect res1.result ++ res2.result

    }).topDown
  }
} */