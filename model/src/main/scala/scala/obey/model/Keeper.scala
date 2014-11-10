package scala.obey.model
import scala.reflect.runtime.{universe => ru}
import Tag._
import scala.obey.Tools.Enrichment._
import scala.obey.Rules._

object Keeper {
	val warners: List[RuleWarning] = List(VarInsteadOfVal, UnusedMember, RenamedDefaultParameter)
	val errs: List[RuleError] = Nil
	val formatters: List[RuleFormat] = Nil

	private def filter[T <: Rule](pos: Set[Tag], neg: Set[Tag])(l: List[T]): List[T] = {
		l.filter(x => !(x.tags & pos).isEmpty && (x.tags & neg).isEmpty)
	}

	def filter(pos: Set[Tag], neg: Set[Tag]): FilterResult = {
		val l1 = filter[RuleWarning](pos, neg)(warners)
		val l2 = filter[RuleError](pos,neg)(errs)
		val l3 = filter[RuleFormat](pos, neg)(formatters)
		FilterResult(l1, l2, l3)
	}

	def filterTag(pos: Set[_ <: ATag], neg: Set[_ <: ATag]): List[RuleWarning] = {
		val tagType = ru.typeOf[ATag]
		warners.filter{
			x => 
				val annot = ru.typeOf[x.type].typeSymbol.annotations
				println(s"The annotation for $x $annot")
				val msgs = annot.filter(a => a.tree.tpe <:< tagType)
				true
		}
		Nil
	}
}