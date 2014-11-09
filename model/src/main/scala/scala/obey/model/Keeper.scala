package scala.obey.model
import scala.reflect.runtime.{universe => ru}

object Keeper {
	var warners: List[RuleWarning] = Nil 
	var errs: List[RuleError] = Nil
	var formatters: List[RuleFormat] = Nil

	//run.typeOf[Object.type].termSymbol.annotations
	def getAnnotation(sym: ru.Symbol): List[ru.Annotation] = {
		sym.annotations
	}

	def filter(pos: List[Class[_ <: Tag]], neg: List[Class[_ <: Tag]])(in: List[Rule]): List[Rule] = {
		val tagType = ru.typeOf[Tag]
		in.filter{
			x => 
				val annot = ru.typeOf[x.type].termSymbol.annotations
				println(s"The annotation for $x $annot")
				val msgs = annot.filter(a => a.tpe == tagType)
				msgs.exists(m => pos.contains(m.getClass)) && !msgs.exists(m => neg.contains(m.getClass))
		}
	}


}