package scala.obey.Tools

import scala.meta.syntactic.ast._
import scala.obey.model._
import scala.language.implicitConversions

object Enrichment {
	implicit class DefnExtractor(tree: Defn) {

		//TODO implement that once we know how to use Member._
		def getName: Term.Name = Term.Name("")
		
		def isAbstract: Boolean = true //tree.mods.contains(Mod.Abstract)
		def isMain: Boolean = tree match {
			case Defn.Def(_, Term.Name("main"), _ , _, _, _) => true
			case _ => false
		}
		//def isConstant: Boolean = tree.mods.contains(Mod.Constant)
	}

	implicit def applyRule(r: Rule): (Tree => List[Msg]) = r.apply

}
