package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.meta.internal.ast._
import scala.obey.model._
import scala.meta.semantic._

@Tag("Dotty") object EarlyToLateInitializer extends Rule {
  def description = "Early initializers should be transformed into normal ones"

  def apply = ???
}