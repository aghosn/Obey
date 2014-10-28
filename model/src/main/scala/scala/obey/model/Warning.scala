package scala.obey.model

trait Warning {
	val rule: Rule
	/*TODO find out how to get the position*/
	val message: String
}