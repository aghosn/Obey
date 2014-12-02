package scala.obey.rules

import scala.meta.syntactic.ast._
import scala.obey.model._
import scala.obey.tools.Utils._

@Tag("Overriding") object RenamedDefaultParameter extends Rule {
  val name = "Renamed Default Parameter"

  def message(t: Tree): Message = Message(s"Renaming parameters $t can lead to unexpected behavior")

  //TODO 
  def apply = ???
}