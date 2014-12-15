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

  /* Processes the options for the plugin*/
  override def processOptions(options: List[String], error: String => Unit) {
    options.foreach {
      opt =>
        if (opt.endsWith(":")) {
          //Nothing to do 
        } else if (opt.startsWith("addRules:")) {
          val opts = opt.substring("addRules:".length)
          Keeper.rules ++= (new Loader(opts)).rules
          reporter.info(NoPosition, "Obey add rules from: " + opts, true)
        } else if (UserOption.optMap.keys.exists(s => opt.startsWith(s))) {
          UserOption.addTags(opt)
          reporter.info(NoPosition, UserOption.toString, true)
        } else {
          reporter.error(NoPosition, "Bad option for obey plugin: '" + opt + "'")
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