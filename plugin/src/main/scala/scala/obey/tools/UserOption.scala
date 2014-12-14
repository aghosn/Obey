package scala.obey.tools

import scala.obey.model.Keeper._
import scala.obey.model.Rule
import scala.obey.model.utils._

object UserOption {

  case class holder(var pos: Set[Tag], var neg: Set[Tag], var use: Boolean)

  val all: holder = holder(Set(), Set(), true)
  val format: holder = holder(Set(), Set(), false)
  val report: holder = holder(Set(), Set(), true) 

  def filterFormat: Set[Rule] = filterT(all.pos ++ format.pos, all.neg ++ format.neg)

  def filterReport: Set[Rule] = filterT(all.pos ++ report.pos, all.neg ++ report.neg)

  /* Get rules for a holder according to the 'all' filter*/
  def getRules(h: holder): Set[Rule] = {
  	if(!h.use) 
  		return Set()
  	val allRules: Set[Rule] = filterT(all.pos, all.neg)
  	filter(h.pos, h.neg)(allRules)
  }

  /* All methods to get the correct rules to apply*/
  def getFormat: Set[Rule] = getRules(format)
  /* Avoids traversing the tree twice for format and warnings*/
  def getReport: Set[Rule] = getRules(report) -- getFormat

  def addTags(hld: holder, tags: (Set[Tag], Set[Tag])) = {
    hld.pos ++= tags._1
    hld.neg ++= tags._2
  }
}
