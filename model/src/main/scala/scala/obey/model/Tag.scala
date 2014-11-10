package scala.obey.model

import scala.annotation.StaticAnnotation

/*	Represents the tags used to handle the rule filtering
 *	The class is final to enable reflection on annotations
 **/
final class Tag(val tag: String) extends StaticAnnotation
