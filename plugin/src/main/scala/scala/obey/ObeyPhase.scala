package scala.obey

import scala.tools.nsc.{ Global, Phase, SubComponent }
import scala.tools.nsc.plugins.{ Plugin => NscPlugin, PluginComponent => NscPluginComponent }
import scala.meta.internal.hosts.scalacompiler.scalahost.Scalahost
import scala.reflect.io.AbstractFile
import scala.obey.model._

trait ObeyPhase {
  self: ObeyPlugin =>

  object ObeyComponent extends NscPluginComponent {
    val global: self.global.type = self.global
    import global._

    val phaseName: String = "obey"
    implicit val h = Scalahost[global.type](global)
    /** TODO check that this is correct*/
    val runsAfter: List[String] = List("persist")

    /**TODO will be responsible for calling the loader*/
    def newPhase(prev: Phase): Phase = new StdPhase(prev) {
      def apply(unit: CompilationUnit) {
        /** TODO implement the meat here*/
        val punit = h.toPalladium(unit.body, classOf[scala.meta.Source])
        //val toApply = Keeper.warners.reduce((r1, r2) => r1 compose r2)
      }
    }
  }
}