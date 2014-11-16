package scala.obey.utils

import scala.obey.tools.Utils._
import scala.obey.model.Keeper._
import scala.obey.model.Rule

object UserOption {

  case class holder(var pos: Set[Tag], var neg: Set[Tag], var use: Boolean)

  val all: holder = holder(Set(), Set(), true)
  val format: holder = holder(Set(), Set(), false)
  val report: holder = holder(Set(), Set(), true)
  val abort: holder = holder(Set(), Set(), false)

  def filterFormat: Set[Rule] = filterT(all.pos ++ format.pos, all.neg ++ format.neg)

  def filterReport: Set[Rule] = filterT(all.pos ++ report.pos, all.neg ++ report.neg)

  def filterAbort: Set[Rule] = filterT(all.pos ++ abort.pos, all.neg ++ abort.neg)

  /* Get rules for a holder according to the 'all' filter*/
  def getRules(h: holder): Set[Rule] = {
  	if(!h.use)
  		return Set()
  	val allRules: Set[Rule] = filterT(all.pos, all.neg)
  	filter(h.pos, h.neg)(allRules)
  }

  /* All methods to get the correct rules to apply*/
  def getFormat: Set[Rule] = getRules(format)
  def getReport: Set[Rule] = getRules(report)
  def getAbort: Set[Rule] = getRules(abort)
}
