package scala.obey.Rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.Tools.Enrichment._

object UnusedMember extends RuleWarning {
	import scala.obey.Tools.Enrichment._
	
	val name: String = "Unused Member"

	def warning(t: Tree): Warning = Warning(s"${t} is not used")


	//Need to know if it is Main, + Difference between Decl and Defn
	private val collectDef = down(
		collect({
			case _ => "a"
			//case x: Defn if !x.isAbstract => x.getName
		}))
		
		
	//TODO finish this	 
	def apply(t: Tree): List[Warning] = {


		Nil
	}
}

