package scala.obey.Rules

import scala.meta._
import tqlscalameta.ScalaMetaTraverser._
import tql.Monoids._
import scala.obey.model._

object RenamedDefaultParameter extends RuleWarning {
	val name : String = "Renamed Default Parameter"

	def warning(t: Tree): Warning = Warning(s"Renaming parameters $t can lead to unexpected behavior")

	//TODO 
	def apply(t: Tree): List[Warning] = Nil
}