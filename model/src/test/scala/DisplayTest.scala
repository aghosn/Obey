import org.scalatest.FunSuite
import scala.reflect.runtime.{universe => ru}
import scala.obey.model._

class DisplayTest extends FunSuite {

  test("Display the names of rules") {
    Keeper.rules.foreach(r => println(r))
  }
} 