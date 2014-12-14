import org.scalatest.FunSuite

import scala.obey.tools._

class testParser extends FunSuite {

  test("Parsing input with OptParser") {
    val res = OptParser.parse("+Var+Play-DCE")
    assert(res._1.size == 2 && res._2.size == 1)
    assert(res._1.contains(Tag("Var")))
    assert(res._2.contains(Tag("DCE")))
  }
}

