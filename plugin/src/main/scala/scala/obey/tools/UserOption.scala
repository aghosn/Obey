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

  def addTags(opts: String): Unit = optMap.find(e => opts.startsWith(e._1)) match {
    case Some((_, h)) if opts.contains("--") => 
      h.use = false
    case Some((_, h)) if opts.contains("++") => 
      h.use = true
      addTags(opts.replace("++", ""))
    case Some((s, h)) if !opts.endsWith(":") => 
      val tags = SetParser.parse(opts.substring(s.length))
      h.pos ++= tags._1
      h.neg ++= tags._2
      h.use = true
    case _ => /*Nothing to do*/
  }

  override def toString = {
    optMap.mkString("\n")
  }

  def disallow: Unit = {
    all.use = false
    report.use = false
    format.use = false
  }
}
