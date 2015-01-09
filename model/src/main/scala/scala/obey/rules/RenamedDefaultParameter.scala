package scala.obey.rules

import scala.meta.internal.ast._
import scala.obey.model._
import scala.obey.model._

@Tag("Overriding") object RenamedDefaultParameter extends Rule {
  def description = "renaming a default parameter"

  def message(t: Tree): Message = Message(s"Renaming parameters $t can lead to unexpected behavior", t)

  //TODO 
  def apply = ???
}