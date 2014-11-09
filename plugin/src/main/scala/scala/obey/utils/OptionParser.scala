package scala.obey.utils

import scala.util.parsing.combinator.syntactical.StandardTokenParsers
import scala.util.parsing.input._
import java.io.StringReader
import scala.obey.model.Tag
import scala.language.implicitConversions

object OptionParser extends StandardTokenParsers {
	lexical.delimiters ++=List("(", ",", ")")

	implicit def trad(s: String): Tag.Value = Tag.withName(s)

	def tag: Parser[Tag.Value] = (
		ident ^^ {case e => e})

	def list: Parser[List[Tag.Value]] = (
		"("~> tag ~ (","~>tag).* <~ ")" ^^ {
			case e1 ~ Nil => List(e1)
			case e1 ~ e2 => e1::e2
		})

	def parse(str: String): Set[Tag.Value] = {
		val tokens = new lexical.Scanner(StreamReader(new StringReader(str)))
		phrase(list)(tokens) match {
			case Success(t, _) => t.toSet
			case e => println(e); Set()
		}
	} 
}
