/**
 * Main component of the compiler plugin.
 *
 *@author Adrien Ghosn
**/

import scala.tools.nsc.{ Global, Phase, SubComponent }
import scala.tools.nsc.plugins.{ Plugin => NscPlugin, PluginComponent => NscPluginComponent }

class Plugin(val global: Global) extends NscPlugin {
	import global._

	val name = "Obey"
	val description = """Compile plugin that checks defined rules against scalameta trees.
	http://github.com/aghosn/Obey for more informations."""
	val components: List[NscPluginComponent] = Nil

	/*TODO 
		Define the NscPluginComponent for the plugin
		How do I make sure that scalahost is ran before ?
		*/


}