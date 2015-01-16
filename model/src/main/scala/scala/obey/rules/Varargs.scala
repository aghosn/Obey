package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.meta.internal.ast._
import scala.obey.model._

@Tag("Dotty", "Argument") object Varargs extends Rule {
  def description = "Varargs are not allowed in Dotty"

  def message(t: Pat.Bind): Message = Message(s"Vararg $t not supported", t)

  def apply = collect {
    case t @ Pat.Bind(_, Pat.Arg.SeqWildcard()) => 
      message(t)
  }.topDown
}