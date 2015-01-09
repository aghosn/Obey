package scala.obey.model

import scala.obey.model._
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
    val tags = TagFilter(pos.map(_.tag.toLowerCase.r.pattern), neg.map(_.tag.toLowerCase.r.pattern))
    l.filter {
      x =>
        val annotSet = getAnnotations(x) + x.getClass.getName.split("\\$").last.toLowerCase
        tags.matches(annotSet)
    }
  }

  def filterT(pos: Set[Tag], neg: Set[Tag]): Set[Rule] = {
    filter(pos, neg)(rules)
  }

}