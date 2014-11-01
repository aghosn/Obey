/**
 *	Model for a Rule. 
 *	TODO continue implementation
 *	@author Adrien Ghosn	
**/
/*TODO Abandon the context thing for the moment*/
/*TODO what would be the best way to enforce a method to apply the rule ?*/
package scala.obey.model

object Rule {
}

/*TODO*/
trait Rule {

	trait RuleState {
		def warnings: List[Warning]
	}

	type State <: RuleState

	trait RuleWarning extends scala.obey.model.Warning {
		val rule: Rule = Rule.this
	}

	type Warning <: RuleWarning

	/** Identifier to pretty print and identity the rule*/
	val name: String
}