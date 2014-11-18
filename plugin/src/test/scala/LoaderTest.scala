import org.scalatest.FunSuite
import scala.obey.utils._
import scala.obey.tools.Utils._
import scala.tools.nsc._
import scala.tools.nsc.reporters.ConsoleReporter

class LoaderTest extends FunSuite {

  test("Load a simple Rule from the model.rules folder") {
    val loader = new Loader("./tests/target/scala-2.11/classes")
    loader.rules.foreach(r => println("a rule: "+r))
    assert(loader.rules.size == 2)
  }

}