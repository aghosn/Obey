package scala.obey.utils

import scala.util.parsing.combinator.syntactical.StandardTokenParsers
import scala.util.parsing.input._
import java.io.StringReader
import scala.obey.tools.Utils._
import scala.language.implicitConversions

object OptParser extends StandardTokenParsers {
  lexical.delimiters ++= List("+", "-")

  implicit def trad(s: String): Tag = new Tag(s)

  object Op extends Enumeration {
    type Op = Value
    val PLUS, MINUS  = Value
  }

  import Op._

  case class Element(o: Op, t: Tag)

  def tag: Parser[Tag] = (
    ident ^^ {case e => e})

  def elem: Parser[Element] = (
    ("+" | "-") ~ tag ^^ {
      case "+" ~ tag => Element(PLUS, tag)
      case "-" ~ tag => Element(MINUS, tag)
    })

  def list: Parser[List[Element]] = elem.*

  def parse(str: String): (Set[Tag], Set[Tag]) = {
    val tokens = new lexical.Scanner(StreamReader(new StringReader(str)))
    phrase(list)(tokens) match {
      case Success(t, _) => 
        val part = t.partition(_.o == PLUS)
        (part._1.map(_.t).toSet, part._2.map(_.t).toSet)
      case e => println(e); (Set(), Set())
    }
  }
}
