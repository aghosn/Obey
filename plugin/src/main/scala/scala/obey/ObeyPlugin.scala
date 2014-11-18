/**
 * 	Main component of the compiler plugin.
 *
 * 	@author Adrien Ghosn
 */
package scala.obey

import scala.tools.nsc.{ Global, Phase, SubComponent }
import scala.tools.nsc.plugins.{ Plugin => NscPlugin, PluginComponent => NscPluginComponent }
import scala.obey.utils._

class ObeyPlugin(val global: Global) extends NscPlugin with ObeyPhase {
  import global._

  val name = "obey"
  val description = """Compiler plugin that checks defined rules against scalameta trees.
  http://github.com/aghosn/Obey for more informations."""
  val components: List[NscPluginComponent] = List[NscPluginComponent](ObeyComponent)

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
    options.foreach {
      o =>
        /* We process the all attributes
         * The user can use -- to prevent its use*/
        if (o.startsWith("all")) {
          val opts = o.substring("all".length)
          val tags = OptParser.parse(opts)
          UserOption.all.pos ++= tags._1
          UserOption.all.neg ++= tags._2

        } else if (o.startsWith("format")) {
          val opts = o.substring("format".length)
          if (!opts.isEmpty && !opts.contains("--")) {
            val tags = OptParser.parse(opts.replace("++", ""))
            UserOption.format.pos ++= tags._1
            UserOption.format.neg ++= tags._2
            UserOption.format.use = true
          }
        } else if (o.startsWith("report")) {
          if (!o.contains("--")) {
            val opts = o.substring("report".length).replace("++", "")
            val tags = OptParser.parse(o.substring("report".length))
            UserOption.report.pos ++= tags._1
            UserOption.report.neg ++= tags._2
          } else {
            UserOption.report.use = false
          }
        } else if (o.equals("-Xfatal-Messages")) {
            /*TODO Report messages*/
        }
    }
  }
}