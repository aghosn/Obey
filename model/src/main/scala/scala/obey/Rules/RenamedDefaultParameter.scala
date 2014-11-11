package scala.obey.Rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.Tools.Wrapper._

object RenamedDefaultParameter extends Rule {
  val name = "Renamed Default Parameter"

  def warning(t: Tree): Warning = Warning(s"Renaming parameters $t can lead to unexpected behavior")

  //TODO 
  def report(t: Tree): List[Warning] = Nil
  def abort(t: Tree){}
  def format(t: Tree){}
}