package scala.obey.utils

import scala.obey.tools.Utils._
import scala.obey.model.Keeper._
import scala.obey.model.Rule

object UserOption {

  case class holder(var pos: Set[Tag], var neg: Set[Tag])

  val all: holder = holder(Set(), Set())
  val format: holder = holder(Set(), Set())
  val report: holder = holder(Set(), Set())
  val abort: holder = holder(Set(), Set())

  def filterFormat: Set[Rule] = filterT(all.pos ++ format.pos, all.neg ++ format.neg)

  def filterReport: Set[Rule] = filterT(all.pos ++ report.pos, all.neg ++ report.neg)

  def filterAbort: Set[Rule] = filterT(all.pos ++ abort.pos, all.neg ++ abort.neg)
}
