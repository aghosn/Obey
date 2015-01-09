import org.scalatest.FunSuite

import scala.obey.tools._
import scala.obey.model._

class testParser extends FunSuite {

  test("Parsing input with OptParser") {
    val res = OptParser.parse("+Var+Play-DCE")
    assert(res._1.size == 2 && res._2.size == 1)
    assert(res._1.contains(Tag("Var")))
    assert(res._2.contains(Tag("DCE")))
  }

  test("Parsing set and filtering") {
    UserOption.addTags("fix:+{List*}")
    println(UserOption.getFormat)
  }

  test("Parsing multiple arguments") {
    val res = SetParser.parse("+{List; Var}")
    println(s"$res from the set Parser")
  }

  test("Parsing with SetParser") {
    val res = SetParser.parse("+{List, Var} - {Dotty}")
    assert(res._1.size == 2 && res._2.size == 1)
  }
}

