package scala.obey.Tools

import scala.meta._
import scala.obey.model._
import scala.language.implicitConversions

object Enrichment {
	implicit class NameExtractor(tree: Defn) {

		//TODO implement that once we know how to use Member._
		def getName: Term.Name = Term.Name("")
		
		def isAbstract: Boolean = tree.mods.contains(Mod.Abstract)
		//def isConstant: Boolean = tree.mods.contains(Mod.Constant)
	}

	implicit def applyRule(r: Rule): (Tree => List[Msg]) = r.apply
}
