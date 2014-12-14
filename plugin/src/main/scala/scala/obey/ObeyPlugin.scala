/**
 * 	Main component of the compiler plugin.
 *
 * 	@author Adrien Ghosn
 */
package scala.obey

import scala.obey.model.Keeper
import scala.obey.model.utils._
import scala.obey.tools._
import scala.tools.nsc.Global
import scala.tools.nsc.plugins.{ PluginComponent => NscPluginComponent }
import scala.meta.internal.hosts.scalac.PluginBase

class ObeyPlugin(val global: Global) extends PluginBase with ObeyPhase {
  import global._

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
   * [warn]: Activated by default.
   *           '--' deactivates it and dominates.
   *           '++' has no effect.
   *           All rules are used by default.
   *           ([+-]Tag)*  will apply these filters on rules selected by 'all'
   */
  override def processOptions(options: List[String], error: String => Unit) {
    options.foreach {
      o =>
        /* We process the all attributes
         * The user can use -- to prevent its use*/
        if (o.endsWith(":")) {
          //Nothing to do 
        } else if (o.startsWith("addRules:")) {
          val opts = o.substring("addRules:".length)
          Keeper.rules ++= (new Loader(opts)).rules
          reporter.info(NoPosition, "Obey add rules from: " + opts, true)
        } else if (UserOption.optMap.keys.exists(s => o.startsWith(s))) {
          UserOption.addTags(o)
        } else {
          reporter.error(NoPosition, "Bad option for obey plugin: '" + o + "'")
        }
    }
  }

  override val optionsHelp: Option[String] = Some("""
    | -P:obey:
    |   all:                Specifies filters for all 
    |   format:             Specifies filters for format
    |   addRules:           Specifies user defined rules
    |   warn:             Specifies filter for warnings
    """)
}