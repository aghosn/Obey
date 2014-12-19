package scala.obey.model

import scala.obey.model.utils._
import scala.obey.rules._
import scala.reflect.runtime.{currentMirror => cm, universe => ru}
import java.util.regex.Pattern

object Keeper {
  var rules: Set[Rule] = Set(VarInsteadOfVal, ListToSet, ListToSetBool)

  /*Enables to efficiently handle the tags*/
  case class TagFilter(pos: Set[Pattern], neg: Set[Pattern]) {

    def matches(s: Set[String]): Boolean = {
      pos.isEmpty || s.exists(e => pos.exists(p => p.matcher(e).matches)) && (!s.exists(e => neg.exists(p => p.matcher(e).matches)))
    } 
  }
  //TODO Still have to rely on the bad trick to get the string
  def filter[T <: Rule](pos: Set[Tag], neg: Set[Tag])(l: Set[T]): Set[T] = {
    val tags = TagFilter(pos.map(_.tag.r.pattern), neg.map(_.tag.r.pattern))
    l.filter {
      x =>
        val annot = cm.classSymbol(x.getClass).annotations.filter(a => a.tree.tpe =:= ru.typeOf[Tag]).flatMap(_.tree.children.tail)
        val annotSet = annot.map(y => ru.show(y).toString).map(_.replaceAll("\"", "")).toSet + x.getClass.getName.split("\\$").last
        tags.matches(annotSet)
    }
  }

  def filterT(pos: Set[Tag], neg: Set[Tag]): Set[Rule] = {
    filter(pos, neg)(rules)
  }

}