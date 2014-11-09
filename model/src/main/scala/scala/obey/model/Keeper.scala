package scala.obey.model
import scala.reflect.runtime.{universe => ru}
import Tag._
import scala.obey.Tools.Enrichment._
import scala.obey.Rules._

object Keeper {
	var warners: List[RuleWarning] = Nil 
	var errs: List[RuleError] = Nil
	var formatters: List[RuleFormat] = Nil

	//run.typeOf[Object.type].termSymbol.annotations
	def getAnnotation(sym: ru.Symbol): List[ru.Annotation] = {
		sym.annotations
	}

	private def filter[T <: Rule](pos: Set[Tag], neg: Set[Tag])(l: List[T]): List[T] = {
		l.filter(x => !(x.tags & pos).isEmpty && (x.tags & neg).isEmpty)
	}

	def filter(pos: Set[Tag], neg: Set[Tag]): FilterResult = {
		val l1 = filter[RuleWarning](pos, neg)(warners)
		val l2 = filter[RuleError](pos,neg)(errs)
		val l3 = filter[RuleFormat](pos, neg)(formatters)
		FilterResult(l1, l2, l3)
	}

}