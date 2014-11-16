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

  override def processOptions(options: List[String], error: String => Unit) {
    options.foreach {
      o =>
        if(o.startsWith("all")) {
          val tags = OptParser.parse(o.substring("all".length))
          UserOption.all.pos ++= tags._1
          UserOption.all.neg ++= tags._2
        } else if(o.startsWith("format")) {
          val tags = OptParser.parse(o.substring("format".length))
          UserOption.format.pos ++= tags._1
          UserOption.format.neg ++= tags._2
        } else if (o.startsWith("report")) {
          val tags = OptParser.parse(o.substring("report".length))
          UserOption.report.pos ++= tags._1
          UserOption.report.neg ++= tags._2
        } else if (o.startsWith("abort")) {
          val tags = OptParser.parse(o.substring("abort".length))
          UserOption.abort.pos ++= tags._1
          UserOption.abort.neg ++= tags._2
        }
    }
  }
}