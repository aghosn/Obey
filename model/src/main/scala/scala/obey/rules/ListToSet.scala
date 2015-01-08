package scala.obey.rules

import scala.meta.tql.ScalaMetaFusionTraverser._

import scala.meta.internal.ast._
import scala.obey.model._
import scala.obey.model.utils._

@Tag("List", "Set") object ListToSet extends Rule {
  val description = "defining List.toSet is defining a Set"

  def message(t: Term.Select): Message = Message(s"The assignment $t creates a useless List", t)

  def apply = {
    (transform {
      case t @ Term.Select(Term.Apply(Term.Name("List"), l), Term.Name("toSet")) =>
        Term.Apply(Term.Name("Set"), l) andCollect message(t)
    }).down
  }
}