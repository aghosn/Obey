package scala.obey

import scala.tools.nsc.Phase
import scala.tools.nsc.plugins.{ PluginComponent => NscPluginComponent }
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
    override val runsAfter = List("typer")
    override val runsRightAfter = Some("persist")
    override def description = "apply obey rules"

    def newPhase(prev: Phase): Phase = new StdPhase(prev) {
      
      def apply(unit: CompilationUnit) {
        
        val punit = unit.body.metadata("scalameta").asInstanceOf[scala.meta.Tree]
        val messageRules = UserOption.getReport
        val formattingRules = UserOption.getFormat
        var warnings: List[Message] = Nil
        
        if (!messageRules.isEmpty)
          warnings ++= messageRules.map(_.apply).reduce((r1, r2) => r1 +> r2)(punit)
        
        var res: MatcherResult[List[Message]] = null
        
        if (!formattingRules.isEmpty) {
          res = formattingRules.map(_.apply).reduce((r1, r2) => r1 + r2)(punit)
          warnings ++= res.result
        }

        reporter.echo("RULES: --------------------------")
        Keeper.rules.foreach(i => reporter.echo("A rule " + i))
      }
    }
  }
}