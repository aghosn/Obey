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

        println("-----------------------------------------------")
        println("Resume of what happened:")
        println("The keeper has those rules: ")
        Keeper.rules.foreach(x => println(x))
        println(s"We selected to report ${UserOption.getReport}")
        println(s"We selected to format ${UserOption.getFormat}")
        println("The warnings we get")
        warnings.foreach(w => println(w))
        println(s"res is ${res}")
        
      }
    }
  }
}