package scala.obey.rules

import scala.meta.syntactic.ast._
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.model._
import scala.obey.tools.Utils._

object RenamedDefaultParameter extends Rule {
  val name = "Renamed Default Parameter"

  def warning(t: Tree): Warning = Warning(s"Renaming parameters $t can lead to unexpected behavior")

  //TODO 
  def report = ???
  def abort = ???
  def format = ???
}