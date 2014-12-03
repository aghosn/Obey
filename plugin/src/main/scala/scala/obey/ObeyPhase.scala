package scala.obey

import scala.obey.tools.Utils._
import scala.obey.rules.VarInsteadOfVal
import scala.obey.utils._
import scala.obey.model._
import scala.obey.utils.UserOption

import scala.tools.nsc.Phase
import scala.tools.nsc.plugins.{ PluginComponent => NscPluginComponent }
import tqlscalameta.ScalaMetaTraverser._

import scala.meta.internal.ast._
import scala.meta._
import org.scalameta.reflection._

trait ObeyPhase {
  self: ObeyPlugin =>

  object ObeyComponent extends NscPluginComponent {
    val global: self.global.type = self.global
    import global._

    val phaseName = "obey_rules"
    override val runsAfter = List("typer")
    override val runsRightAfter = Some("convert")
    override def description = "apply obey rules"

    def showTree(x: scala.meta.Tree) = scala.meta.syntactic.show.ShowOps(x).show[syntactic.show.Raw]

    def newPhase(prev: Phase): Phase = new StdPhase(prev) {

      def apply(unit: CompilationUnit) {
        val path = unit.source.path
        val punit = unit.body.metadata("scalameta").asInstanceOf[scala.meta.Tree]
        val messageRules = UserOption.getReport
        val formattingRules = UserOption.getFormat
        var warnings: List[Message] = Nil

        if (!messageRules.isEmpty)
          warnings ++= messageRules.map(_.apply).reduce((r1, r2) => r1 +> r2)(punit)

        var res: MatcherResult[List[Message]] = null

        if (!formattingRules.isEmpty) {
          res = formattingRules.map(_.apply).reduce((r1, r2) => r1 + r2)(punit)
          if (res.tree.isDefined && !res.result.isEmpty) {
            Persist.archive(path)
            Persist.persist(path, res.tree.get.toString)
            warnings ++= res.result.map(m => Message("CORRECTED: " + m.message))
          } else {
            warnings ++= res.result
          }
        }

        /*TODO Debugging */
        /*println("-----------------------------------------------")
        println(s"We selected to report ${UserOption.getReport}")
        println(s"We selected to format ${UserOption.getFormat}")*/

        /*Reporting the errors*/
        warnings.foreach(m => reporter.warning(NoPosition, m.message))

        /*TODO implement persistence for reformatting*/
      }
    }
  }
}