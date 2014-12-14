package scala.obey.tools

import scala.meta.internal.ast._
import scala.obey.model._
import scala.language.implicitConversions

import scala.annotation.StaticAnnotation
import scala.reflect.internal.util.NoPosition

object Enrichment {

  implicit class DefnExtractor(tree: Defn) {

    def isAbstract: Boolean = true //tree.mods.contains(Mod.Abstract)

    def isMain: Boolean = tree match {
      case Defn.Def(_, Term.Name("main"), _, _, _, _) => true
      case _ => false
    }

    def isValueParameter: Boolean = tree.parent.map(p => p.isInstanceOf[Member.Method]).getOrElse(false)

    /*TODO find how to do that*/
    def isConstructorArg: Boolean = true //tree.parent.map(p => p.isInstanceOf[Member.Ctor]).getOrElse(false)

    /*TODO How do we test for tpe == Unit ?*/
    def isUnit: Boolean = tree match {
      case x: Defn.Def => true
      case _ => true
    }

    /*TODO once we know how to get the type */
    def getType: Type.Name = Type.Name("Unit")

  }

  def getPos(t: scala.meta.Tree): scala.reflect.internal.util.Position = {
    try {
      import scala.language.reflectiveCalls
      val scratchpads = t.asInstanceOf[{ def internalScratchpads: Map[_, _] }].internalScratchpads
      val associatedGtree = scratchpads.values.toList.head.asInstanceOf[List[_]].collect { case gtree: scala.reflect.internal.SymbolTable#Tree => gtree }.head
      associatedGtree.pos
    } catch {
      case e: Exception => NoPosition
    }
  }

}
