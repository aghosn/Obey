package scala.obey.tools

import java.io.StringReader

import scala.language.implicitConversions
import scala.obey.model._
import scala.util.parsing.combinator.syntactical.StandardTokenParsers
import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input._

object SetParser extends RegexParsers {
  //lexical.delimiters ++= List("+", "-", ",", "{","}", ";", "*")

  implicit def trad(s: String): Tag = new Tag(s)

  object Op extends Enumeration {
    type Op = Value
    val PLUS, MINUS  = Value
  }

  import scala.obey.tools.OptParser.Op._

  def tag: Parser[Tag] = """[\w\*]+""".r  ^^ {case e => e.replace("*", ".*") }

  def tags: Parser[Set[Tag]] = (
      "{" ~> tag ~ ("[;,]".r ~> tag).* <~ "}" ^^ {
      case e ~ Nil => Set(e)
      case e ~ e1 => Set(e) ++ e1.toSet

    })

  def set: Parser[(Set[Tag], Boolean)] = (
    "+" ~> tags ^^ { case e => (e, true)}
    |"-" ~> tags ^^ {case e => (e, false)}
    )

  def res: Parser[List[(Set[Tag], Boolean)]] = set.*

  def parse(str: String): (Set[Tag], Set[Tag]) = {
    /*val tokens = new lexical.Scanner(StreamReader(new StringReader(str)))
    phrase(res)(tokens)*/ parseAll(res, str) match {
      case Success(t, _) => 
        val (pos, neg) = t.partition(_._2 == true)
        val posSet = pos.map(_._1).fold(Set())((x, y) => x ++ y)
        val negSet = neg.map(_._1).fold(Set())((x, y) => x ++ y)
        (posSet, negSet)
      case e => println(e); (Set(), Set())
    }
  }
}
