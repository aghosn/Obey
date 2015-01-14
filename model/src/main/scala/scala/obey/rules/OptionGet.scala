package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.language.reflectiveCalls
import scala.meta.internal.ast._
import scala.meta.semantic._
import scala.obey.model._

/*
@Tag("Option", "Option#get") class OptionGet(implicit c: Context) extends Rule {

  def description = "Use combinators on options instead of explicit unwrapping"

  def message(t: Term.Ref) = Message(s"${t} should use combinators on Option", t)

  /*TODO Implement this*/
  def isOption(t: Type.Name): Boolean = true

  def apply = {
      (collect {
        case get: Term.Ref if get == termOf("Option.get") =>
          message(get)
      }).topDown
  }

}*/