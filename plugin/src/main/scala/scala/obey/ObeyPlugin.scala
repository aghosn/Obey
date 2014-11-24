/**
 * 	Main component of the compiler plugin.
 *
 * 	@author Adrien Ghosn
 */
package scala.obey

import scala.tools.nsc.{ Global, Phase, SubComponent }
import scala.tools.nsc.plugins.{ Plugin => NscPlugin, PluginComponent => NscPluginComponent }
import scala.obey.utils._
import scala.obey.model.Keeper

class ObeyPlugin(val global: Global) extends NscPlugin with ObeyPhase {
  import global._

  val format = "format:"
  val all = "all:"
  val report = "report:"
  val fatal = "-Xfatal-Messages"

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
        /*TODO too much repetition here, need to clean that up*/
        /* We process the all attributes
         * The user can use -- to prevent its use*/
        if (o.startsWith(all)) {
          
          val opts = o.substring(all.length)
          val tags = OptParser.parse(opts)
          UserOption.addTags(UserOption.all, tags)

        } else if (o.startsWith(format) && !o.contains("--")) {
          
          val opts = o.substring(format.length)
          val tags = OptParser.parse(opts.replace("++", ""))
          UserOption.addTags(UserOption.format, tags)

        } else if (o.startsWith(report)) {
          
          if (!o.contains("--")) {
            val opts = o.substring(report.length).replace("++", "")
            val tags = OptParser.parse(opts)
            UserOption.addTags(UserOption.report, tags)
          } else {
            UserOption.report.use = false
          }
          
        } else if (o.equals(fatal)) {
          /*TODO Report messages*/
        } else if (o.startsWith("addRules:")) {
          val opts = o.substring("addRules:".length)
          Keeper.rules ++= (new Loader(opts)).rules
        } else {
          println("PROBLEMS")
        }
    }
  }

  override val optionsHelp: Option[String] = Some("""
    | -P:obey:
    |   all:                Specifies filters for all 
    |   format:             Specifies filters for format
    |   addRules:           Specifies user defined rules
    |   report:             Specifies filter for warnings
    |   -Xfatal-Messages    Abort on any warning 
    """)

}