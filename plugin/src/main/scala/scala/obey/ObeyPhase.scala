package scala.obey

import scala.tools.nsc.{ Global, Phase, SubComponent }
import scala.tools.nsc.plugins.{ Plugin => NscPlugin, PluginComponent => NscPluginComponent }
import scala.meta.internal.hosts.scalacompiler.scalahost.Scalahost
import scala.obey.model._
import scala.obey.utils.UserOption
import tqlscalameta.ScalaMetaTraverser._
import scala.obey.tools.Utils._

trait ObeyPhase {
  self: ObeyPlugin =>

  object ObeyComponent extends NscPluginComponent {
    val global: self.global.type = self.global
    import global._

    val phaseName = "obey_rules"
    implicit val h = Scalahost[global.type](global)
    /** TODO check that this is correct*/
    override val runsAfter: List[String] = List("typer")
    override def description = "apply obey rules"

    /**TODO will be responsible for calling the loader*/
    def newPhase(prev: Phase): Phase = new StdPhase(prev) {
      def apply(unit: CompilationUnit) {
        /** TODO implement the meat here*/
        val punit = h.toPalladium(unit.body, classOf[scala.meta.Source])
        val formattingRules = UserOption.getFormat.map(_.apply).reduce((r1, r2) => r1 + r2)
        val messageRules = UserOption.getReport.map(_.apply).reduce((r1, r2) => r1 +> r2)
        val resultTree = formattingRules(punit)
        val messages: List[Message] = resultTree.result ++ messageRules(punit)
        //TODO Handle messages
        //Persist the trees 
        //TODO testing that it loads the rules correctly
        println("HEEEEEEEEEEEEEEEEEEEELLLLLLLLLLLLLLLLLLLLOOOOOOOOOOOOOO")
        reporter.echo("RULES: --------------------------")
        Keeper.rules.foreach(i => reporter.echo("A rule "+i))
      }
    }
  }
}