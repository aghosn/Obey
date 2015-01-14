package scala.obey.tools

import scala.language.implicitConversions
import scala.meta.internal.ast._

object Enrichment {

  implicit class DefnExtractor(tree: Defn) {

    def isAbstract: Boolean = true //tree.mods.contains(Mod.Abstract)

    def isMain: Boolean = tree match {
      case Defn.Def(_, Term.Name("main"), _, _, _, _) => true
      case _ => false
    }

    def isValueParameter: Boolean = true //tree.parent.map(p => p.isInstanceOf[Member.Method]).getOrElse(false)

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

}
