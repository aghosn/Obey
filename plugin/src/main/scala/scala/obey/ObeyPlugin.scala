/**
 * 	Main component of the compiler plugin.
 *
 * 	@author Adrien Ghosn
 */
package scala.obey

import scala.meta.internal.hosts.scalac.PluginBase
import scala.tools.nsc.Global
import scala.tools.nsc.plugins.{ PluginComponent => NscPluginComponent }
import scala.obey.utils._
import scala.obey.model.Keeper
import scala.obey.tools.Utils._

class ObeyPlugin(val global: Global) extends PluginBase with ObeyPhase {
  import global._
  val format = "format:"
  val all = "all:"
  val report = "report:"

  val name = "obey"
  val description = """Compiler plugin that checks defined rules against scala meta trees.
  http://github.com/aghosn/Obey for more information."""
  val components = List[NscPluginComponent](ConvertComponent, ObeyComponent)

  /**
   * Processes the options for the plugin
   * [all]: By default, all rules are used as Message
   *        ([+-]Tag)* filters the rules
   * [format]: Deactivated by default. To activate put a '++'.
   *           '--' deactivates the option and dominates any other entry
   *           ([+-]Tag)*  will apply these filters on rules selected by 'all'
   * [report]: Activated by default.
   *           '--' deactivates it and dominates.
   *           '++' has no effect.
   *           All rules are used by default.
   *           ([+-]Tag)*  will apply these filters on rules selected by 'all'
   * [abort]: Deactivated by default. To activate put a '++'.
   *          '--' deactivates it and dominates.
   *          ([+-]Tag)*  will apply these filters on rules selected by 'all'
   */
  override def processOptions(options: List[String], error: String => Unit) {
    
    case class h(pos: Set[Tag], neg:Set[Tag]) {
      override def toString = {
        "+"+pos.mkString("(",",", ")")+"-"+neg.mkString("(", ",", ")")
      }
    }
    def conv(x: (Set[Tag], Set[Tag])) = h(x._1, x._2)

    options.foreach {
      o =>
        /* We process the all attributes
         * The user can use -- to prevent its use*/
        if (o.startsWith(all)) {
          val opts = o.substring(all.length)
          val tags = OptParser.parse(opts)
          UserOption.addTags(UserOption.all, tags)
          reporter.info(NoPosition, "Obey all filters: "+conv(tags), true)
        } else if (o.startsWith(format) && !o.contains("--")) {
          val opts = o.substring(format.length)
          val tags = OptParser.parse(opts.replace("++", ""))
          UserOption.addTags(UserOption.format, tags)
          UserOption.format.use = true
          reporter.info(NoPosition, "Obey format filters: "+conv(tags), true)
        } else if (o.startsWith(report)) {
          if (!o.contains("--")) {
            val opts = o.substring(report.length).replace("++", "")
            val tags = OptParser.parse(opts)
            UserOption.addTags(UserOption.report, tags)
            reporter.info(NoPosition, "Obey report filters: "+conv(tags), true)
          } else
            UserOption.report.use = false
        } else if (o.startsWith("addRules:")) {
          val opts = o.substring("addRules:".length)
          Keeper.rules ++= (new Loader(opts)).rules
          reporter.info(NoPosition, "Obey add rules from: "+opts, true)
        } else {
          reporter.error(NoPosition, "Bad option for obey plugin: '"+o+"'")
        }
    }
  }

  override val optionsHelp: Option[String] = Some("""
    | -P:obey:
    |   all:                Specifies filters for all 
    |   format:             Specifies filters for format
    |   addRules:           Specifies user defined rules
    |   report:             Specifies filter for warnings
    """)
}