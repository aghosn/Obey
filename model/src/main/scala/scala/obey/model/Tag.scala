package scala.obey.model

import scala.annotation.StaticAnnotation

/*TODO maybe add a description too, and a rule to assign this annotation to in the class impl*/
trait Tag extends StaticAnnotation {
	val name: String
}

case class VarTag(val name: String = "Var Rule") extends Tag
/*TODO encapsulate the case class implementing this into objects*/