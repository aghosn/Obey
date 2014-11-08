package scala.obey.model

object Keeper {
	var warners: List[RuleWarning] = Nil 
	var errs: List[RuleError] = Nil
	var formatters: List[RuleFormat] = Nil
}