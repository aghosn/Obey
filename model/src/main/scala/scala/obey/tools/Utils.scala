package scala.obey.tools
import scala.annotation.StaticAnnotation

object Utils {
  /* Message type*/
  case class Message(message: String)

  /*  Represents the tags used to handle the rule filtering*/
  case class Tag(val tag: String, val tags: String*) extends StaticAnnotation
}