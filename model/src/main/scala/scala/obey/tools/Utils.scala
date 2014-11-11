package scala.obey.tools
import scala.annotation.StaticAnnotation

object Utils {
  /* Warning type*/
  case class Warning(message: String)

  /*  Represents the tags used to handle the rule filtering
 *  The class is final to enable reflection on annotations
 **/
  case class Tag(val tag: String) extends StaticAnnotation
}