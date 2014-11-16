package scala.obey.utils

import scala.tools.nsc.{ Global}
import scala.reflect.runtime.{universe => ru}
import scala.obey.model.Rule

class Loader(val global: Global, val folder: String) {

  lazy val files: Array[java.io.File] = new java.io.File(folder).listFiles.filter(_.getName.endsWith(".scala"))
  lazy val classLoader = new java.net.URLClassLoader(files.map(_.toURI.toURL), getClass.getClassLoader)
  
  //private lazy val mirror = ru.runtimeMirror(classLoader)
  lazy val run = new global.Run 

  def loadUserRules: List[Any] = {
    run.compile(files.map(_.getName).toList)
    val names: List[String] = getNames(files.map(_.getName).toList)
    names.map(classLoader.loadClass(_).newInstance)
  }

  /* Extracts the names from the files*/
  def getNames(l: List[String]): List[String] = {
    l.map{
      name => 
        val start = name.lastIndexOf("/") + 1

        val end = name.lastIndexOf(".scala") 

        if(start >= end )
          ""
        else 
          name.substring(start, end)
    }
  }
}