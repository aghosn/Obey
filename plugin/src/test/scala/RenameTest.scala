import scala.obey.utils._
import org.scalatest.FunSuite
import java.io._


class RenameTest extends FunSuite {

  val code = 
    """
      object bla {

        def main(args: Array[String]) {
          println("Hello world!")
        }
      }
    """
  test("Renaming file") {
    val root = new File("tests/resources")

    /*Change to .scala.old*/
    val files = root.listFiles.filter(f => f.isFile && f.getName.endsWith(".scala")).toList
    files.foreach{
      f => 
        Persist.archive(f.getCanonicalPath)
    }
    assert(root.listFiles.filter(f => f.isFile && f.getName.endsWith(".scala")).toList.isEmpty)

    /*Get back to .scala*/
    val files1 = root.listFiles.filter(f => f.isFile && f.getName.endsWith(".scala.old")).toList
    files1.foreach {
      f => 
        Persist.unarchive(f.getCanonicalPath)
    }

    assert(root.listFiles.filter(f => f.isFile && f.getName.endsWith(".old")).toList.isEmpty)
  }

  test("Persisting a file") {
    val dir = "tests/resources"
    val file = "bla.scala"
    Persist.persist(dir + File.separatorChar + file, code)
    val root = new File(dir)
    assert(root.listFiles.filter(f => f.isFile && f.getName.endsWith(file)).toList.size == 1)
    val f = new File(dir + File.separatorChar + file)
    assert(f.exists())
    assert(f.delete())
    assert(!f.exists())
  }
}