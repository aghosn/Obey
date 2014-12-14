package scala.obey.tools

import scala.obey.model.Keeper._
import scala.obey.model.Rule
import scala.obey.model.utils._

object UserOption {

  private val all = Holder(Set(), Set(), true)
  private val format = Holder(Set(), Set(), false)
  private val report = Holder(Set(), Set(), true)

  val optMap: Map[String, Holder] = Map("all:" -> all, "fix:" -> format, "warn:" -> report)

  /* Get rules for a holder according to the 'all' filter*/
  def getRules(h: Holder): Set[Rule] = {
    if (!h.use)
      return Set()
    val allRules: Set[Rule] = filterT(all.pos, all.neg)
    filter(h.pos, h.neg)(allRules)
  }

  /* All methods to get the correct rules to apply*/
  def getFormat: Set[Rule] = getRules(format)
  /* Avoids traversing the tree twice for format and warnings*/
  def getReport: Set[Rule] = getRules(report) -- getFormat

  /* Supposed to be called when opts has a valid prefix*/
  def addTags(opts: String): Unit = opts match {
    case x if x.contains("--") =>
      val opt = optMap.find(e => opts.startsWith(e._1)).get
      opt._2.use = false
    case x if x.contains("++") => addTags(x.replace("++", ""))
    case o if !o.endsWith(":") =>
      val opt = optMap.find(e => opts.startsWith(e._1)).get
      val tags = OptParser.parse(opts.substring(opt._1.length))
      opt._2.pos ++= tags._1
      opt._2.neg ++= tags._2
    case _ => /*Nothing to do*/
  }
}
