package scala.obey.tools

import java.io._
import java.net._
import java.util.jar._

import scala.obey.model.Rule

class Loader2(implicit c: scala.meta.semantic.Context) {

  def printClasses(jars: List[String]) = {
    //Simple print of the list of jars
    println("The list of classes that we get: ")
    jars.map { x =>
      extractJar(new File(x))
    }

  }

  def copyStream(istream: InputStream, ostream: OutputStream): Unit = {
    var bytes = new Array[Byte](1024)
    var len = -1
    while ({ len = istream.read(bytes, 0, 1024); len != -1 })
      ostream.write(bytes, 0, len)
  }

  def extractJar(file: File): List[Rule] = {
    val basename = file.getName.substring(0, file.getName.lastIndexOf("."))
    val todir = new File(file.getParentFile, "/tmp/"+basename)
    todir.mkdirs()

    println("Extracting " + file + " to " + todir)
    val jar = new JarFile(file)
    val enu = jar.entries
    while (enu.hasMoreElements) {
      val entry = enu.nextElement
      val entryPath =
        if (entry.getName.startsWith(basename)) entry.getName.substring(basename.length)
        else entry.getName

      println("Extracting to " + todir + "/" + entryPath)
      if (entry.isDirectory) {
        new File(todir, entryPath).mkdirs
      } else {
        try {
          val istream = jar.getInputStream(entry)
          val ostream = new FileOutputStream(new File(todir, entryPath))
          copyStream(istream, ostream)
          ostream.close
          istream.close
        } catch {
          case e: Exception => /*TODO*/
        }
      }
    }

    val loader = new Loader(todir.getCanonicalPath)
    val rules = loader.rules
    removeAll(todir.getAbsolutePath)
    rules
  }

   def removeAll(path: String) = {
    def getRecursively(f: File): Seq[File] = 
      f.listFiles.filter(_.isDirectory).flatMap(getRecursively) ++ f.listFiles
    getRecursively(new File(path)).foreach{f => 
      if (!f.delete()) 
        throw new RuntimeException("Failed to delete " + f.getAbsolutePath)}
  }


  
}