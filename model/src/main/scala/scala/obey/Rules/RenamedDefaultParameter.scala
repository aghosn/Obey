package scala.obey.Rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._

object RenamedDefaultParameter extends RuleWarning {
  val name: String = "Renamed Default Parameter"

  def warning(t: Tree): Warning = Warning(s"Renaming parameters $t can lead to unexpected behavior")

  //TODO 
  def apply(t: Tree): List[Warning] = Nil
}