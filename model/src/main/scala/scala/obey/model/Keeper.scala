package scala.obey.model

import scala.obey.model.utils._
import scala.obey.rules._
import scala.reflect.runtime.{currentMirror => cm, universe => ru}

object Keeper {
  var rules: Set[Rule] = Set(VarInsteadOfVal, ListToSet)

  /*Enables to efficiently handle the tags*/
  case class TagFilter(full: Set[String], start: Set[String], end: Set[String], contains: Set[String]) {

    def isEmpty: Boolean = full.isEmpty && start.isEmpty && end.isEmpty && contains.isEmpty

    def &(s: Set[String]): Set[String] = {
      val s1 = (s & full) ++ (s.filter(t => start.exists(t1 => t.startsWith(t1))))
      val s2 = (s.filter(t => end.exists(t1 => t.endsWith(t1)))) ++ (s.filter(t => contains.exists(t1 => t.contains(t1))))
      s1 ++ s2 
    } 
  }
  //TODO Still have to rely on the bad trick to get the string
  def filter[T <: Rule](pos: Set[Tag], neg: Set[Tag])(l: Set[T]): Set[T] = {
    val posSet = getTagFilter(pos.map(_.tag.toLowerCase))
    val negSet = getTagFilter(neg.map(_.tag.toLowerCase))
    l.filter {
      x =>
        val annot = cm.classSymbol(x.getClass).annotations.filter(a => a.tree.tpe =:= ru.typeOf[Tag]).flatMap(_.tree.children.tail)
        val annotSet = annot.map(y => ru.show(y).toString).map(_.replaceAll("\"", "").toLowerCase).toSet + x.getClass.getName.split("\\$").last.toLowerCase
        (posSet.isEmpty || !(posSet & annotSet).isEmpty) && (negSet & annotSet).isEmpty
    }
  }

  /*Generate a tag filter according to the set of string*/
  def getTagFilter(s: Set[String]): TagFilter = {
    val (comp, full) = s.partition(_.contains("*"))
    val end = comp.filter(t => t.startsWith("*") && !t.endsWith("*")).map(_.replace("*", "").toLowerCase)
    val start = comp.filter(t => !t.startsWith("*") && t.endsWith("*")).map(_.replace("*", "").toLowerCase)
    val contains = comp.filter(t => t.startsWith("*") && t.endsWith("*")).map(_.replace("*","").toLowerCase)
    TagFilter(full.map(_.toLowerCase), start, end, contains)
  }

  def filterT(pos: Set[Tag], neg: Set[Tag]): Set[Rule] = {
    filter(pos, neg)(rules)
  }

}