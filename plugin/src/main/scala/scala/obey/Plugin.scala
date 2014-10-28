/**
 * Main component of the compiler plugin.
 *
 *@author Adrien Ghosn
**/
package scala.obey 

import scala.tools.nsc.{ Global, Phase, SubComponent }
import scala.tools.nsc.plugins.{ Plugin => NscPlugin, PluginComponent => NscPluginComponent }

class Plugin(val global: Global) extends NscPlugin {
	import global._

	val name = "Obey"
	val description = """Compiler plugin that checks defined rules against scalameta trees.
	http://github.com/aghosn/Obey for more informations."""
	val components: List[NscPluginComponent] = List(ObeyPluginComponent)


	private object ObeyPluginComponent extends NscPluginComponent {
		import global._

		val global = Plugin.this.global
		val phaseName: String = "obey"
		/** TODO check that this is correct*/
		val runsAfter: List[String] = List("persist")
		
		/**TODO will be responsible for calling the loader*/
		def newPhase(prev: Phase): Phase = new StdPhase(prev) {
			def apply(unit: CompilationUnit) {
				/** TODO implement the meat here*/
			}
		}	
	}
}