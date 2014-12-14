package scala.obey.tools
import scala.annotation.StaticAnnotation
import scala.obey.tools.Enrichment._

object Utils {
  /* Message type*/
  case class Message(message: String, tree: scala.meta.Tree) {
    val position = getPos(tree)
  }

  /*  Represents the tags used to handle the rule filtering*/
  case class Tag(val tag: String, val tags: String*) extends StaticAnnotation {
    override def toString = {
      "Tag("+(tag::tags.toList).mkString(",")+")"
    }
  }
}