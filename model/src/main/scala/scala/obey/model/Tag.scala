package scala.obey.model

import scala.annotation.StaticAnnotation

trait Tag extends StaticAnnotation {
	val name: String
}