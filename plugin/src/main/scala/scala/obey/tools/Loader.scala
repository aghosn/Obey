package scala.obey.tools

import java.io._
import java.net._

import scala.obey.model.Rule

class Loader(val folder: String) {

  val root = new File(folder)
  val cl = new URLClassLoader(Array(root.toURI.toURL), getClass.getClassLoader)

  def getClasses(dir: File): List[Class[_]] = {

    val dirs = dir.listFiles.filter(_.isDirectory).toList
    val classFiles = dir.listFiles.filter(f => f.isFile && f.getName.endsWith(".class")).toList
    dirs.flatMap(getClasses) ++ classFiles.map {
      cf =>
        val className = {
          if (root.getCanonicalPath == dir.getCanonicalPath) {
            cf.getName.stripSuffix(".class")
          } else {
            val packagePath = dir.getCanonicalPath.substring(root.getCanonicalPath.length + 1)
            val packageName = packagePath.replace(File.separator, ".")
            packageName + "." + cf.getName.stripSuffix(".class")
          }
        }
         cl.loadClass(className)
    }
  }

  val ruleClasses = getClasses(root).filter(c => classOf[Rule].isAssignableFrom(c))
  
  /*Rules can be defined as object or classes*/
 /* val rules = ruleClasses.map{ c =>
    val instance = try {
      c.getDeclaredField("MODULE$").get(null)
    } catch{
      case ex:NoSuchFieldException => c.newInstance()
    }
    instance.asInstanceOf[Rule]
  }*/


  def rules(implicit context: scala.meta.semantic.Context): List[Rule] = {
    ruleClasses.map{ c =>
      val instance = try c.getDeclaredField("MODULE$").get(null)
      catch{
        case ex:NoSuchFieldException =>
          try {
            c.newInstance()
          }catch{
            case ee: Exception =>
              //println("THE CONSTRUCTORS "+c.getConstructors)
              //println("The one selected "+c.getConstructors()(0))
              c.getConstructors()(0).newInstance(context)
          }
      }
      instance.asInstanceOf[Rule]
    }
  }
}