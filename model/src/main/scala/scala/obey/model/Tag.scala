package scala.obey.model

import scala.annotation.StaticAnnotation

/*TODO maybe add a description too, and a rule to assign this annotation to in the class impl*/
trait Tag extends StaticAnnotation {
	val name: String
}

/*TODO encapsulate the case class implementing this into objects*/