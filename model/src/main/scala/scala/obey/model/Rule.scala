/**
 *	Model for a Rule. 
 *	TODO continue implementation
 *	@author Adrien Ghosn	
**/
/*Abandon the context thing for the moment*/
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