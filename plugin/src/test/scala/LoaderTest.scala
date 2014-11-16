import org.scalatest.FunSuite
import scala.obey.utils._
import scala.obey.tools.Utils._
import scala.tools.nsc._

class LoaderTest extends FunSuite {

  val settings = new Settings
  val scalaLibraryPath = "/home/aghosn/.ivy2/cache/org.scala-lang/scala-library/jars/scala-library-2.11.2.jar"
  settings.bootclasspath.append(scalaLibraryPath)
  settings.classpath.append(scalaLibraryPath)
  //val reporter = new ConsoleReporter(settings)

  val global = new Global(settings/*, reporter, "testing"*/)
  
  test("Extract names from files") {
    val loader = new Loader(global, "bitching")
    val res: String = loader.getNames(List("/home/aghosn/Programs/Scala/Obey/Rule.scala")).head  
    assert(res.equals("Rule"), s"What we get: ${res}")
    val res1: String = loader.getNames(List("/home.scala/aghosn/Rule.scala")).head
    assert(res1.equals("Rule"))
    val res3: List[String] = loader.getNames(List("/", "", " / ", "Rule", "Rule.scala"))
    val expected = List("", "", "", "", "Rule")
    res3.zip(expected).foreach(p => assert(p._1.equals(p._2)))
  }

  test("Trying to load a single class") {
    
    val loader = new Loader(global, "/home/aghosn/Documents/Programs/Scala/Rules")
    loader.files.foreach(f => println(f))
    val r = loader.loadUserRules

  }
}