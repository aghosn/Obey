package scala.obey.model

import scala.annotation.StaticAnnotation

/*TODO maybe add a description too, and a rule to assign this annotation to in the class impl*/
/*abstract class Tag extends StaticAnnotation {
	val name: String
}*/

//case class VarTag(name: String = "Var Rule") extends Tag
/*TODO encapsulate the case class implementing this into objects*/

object Tag extends Enumeration {
	type Tag = Value
	val Var, DCE, Play, akka = Value
}