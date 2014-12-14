package scala.obey.rules

import scala.meta.internal.ast._
import scala.obey.model._
import scala.obey.model.utils._

@Tag("Overriding") object RenamedDefaultParameter extends Rule {
  val name = "RenamedDefaultParameter: renaming a default parameter"

  def message(t: Tree): Message = Message(s"Renaming parameters $t can lead to unexpected behavior", t)

  //TODO 
  def apply = ???
}