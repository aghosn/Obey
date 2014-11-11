package scala.obey.tools

import scala.meta.syntactic.ast._
import scala.obey.model._
import scala.language.implicitConversions

import scala.annotation.StaticAnnotation

object Enrichment {
  implicit class DefnExtractor(tree: Defn) {

    //TODO implement that once we know how to use Member._
    def getName: Term.Name = tree match {
      case t: Defn.Def => t.name
      case t: Defn.Procedure => t.name
      case t: Defn.Macro => t.name
    }

    def isAbstract: Boolean = true //tree.mods.contains(Mod.Abstract)

    def isMain: Boolean = tree match {
      case Defn.Def(_, Term.Name("main"), _, _, _, _) => true
      case _ => false
    }

    def isValueParameter: Boolean = tree.parent.isInstanceOf[Member.Method]

    /*TODO find how to do that*/
    def isConstructorArg: Boolean = true //tree.parent.isInstanceOf[Member.Ctor]

  }

}
