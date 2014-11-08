package scala.obey.Rules

import scala.meta._
import tqlscalameta.ScalaMetaTraverser._
import tql.Monoids._
import scala.obey.model._
import scala.obey.Tools.Enrichment._

object UnusedMember extends RuleWarning {
	val name: String = "Unused Member"

	def warning(t: Tree): Warning = Warning(s"${t} is not used")


	//Need to know if it is Main, + Difference between Decl and Defn
	private val collectDef = down(
		collect({
			case x: Defn if !x.isAbstract => x.getName
		}))
		
		
	//TODO finish this	 
	def apply(t: Tree): List[Warning] = {


		Nil
	}
}

