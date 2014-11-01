package scala.obey.Rules

import scala.obey.model.Rule
import scala.meta._
import tql._
import tqlscalameta.ScalaMetaTraverser._

class VarInsteadOfVal(tree: Tree) extends Rule {
	val name: String = "Var Instead of Val"

	case class Warning(tree: Tree) extends RuleWarning {
		val message: String = s"The 'var' $tree was never reassigned and should therefore be a 'val'"
	}

	object State extends RuleState {
		var warningsList: List[Warning] = Nil 
		def warnings: List[Warning] = warningsList
		def addWarning(w: Warning) = warningsList ::= w
	}

	/*TODO make that work*/
	val vars = down(
				collect({case a: Defn.Var => a})
			)
}