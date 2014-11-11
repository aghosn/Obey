package scala.obey.Tools

import scala.meta.syntactic.ast._
import scala.obey.model._
import scala.language.implicitConversions

import scala.annotation.StaticAnnotation

object Enrichment {
  implicit class DefnExtractor(tree: Defn) {

    //TODO implement that once we know how to use Member._
    def getName: Term.Name = tree match {
      //case Defn.Def(_, n, _,_,_,_) => n
      case t: Defn.Def => t.name
      case t: Defn.Procedure => t.name
      case t: Defn.Macro => t.name
      /*Default case is painful*/
      case _ => Term.Name("")
    }

    def isAbstract: Boolean = true //tree.mods.contains(Mod.Abstract)

    def isMain: Boolean = tree match {
      case Defn.Def(_, Term.Name("main"), _, _, _, _) => true
      case _ => false
    }

    def isValueParameter: Boolean = tree.parent match {
      case Some(t) =>
        t match {
          case d: Defn.Def => true
          case d: Defn.Procedure => true
          case _ => false
        }
      case None => false
    }

    /*TODO find how to do that*/
    def isConstructorArg: Boolean = tree.parent match {
      case _ => true
    }

  }

  implicit def applyRule(r: Rule): (Tree => List[Msg]) = r.apply

  case class FilterResult(warners: List[RuleWarning], errs: List[RuleError], formatters: List[RuleFormat])

}
