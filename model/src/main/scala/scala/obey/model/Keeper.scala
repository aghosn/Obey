package scala.obey.model

import scala.reflect.runtime.{ universe => ru }
import scala.reflect.runtime.{ currentMirror => cm }
import scala.obey.rules._
import scala.obey.tools.Enrichment._
import scala.obey.tools.Utils._

object Keeper {
  var rules: Set[Rule] = Set(VarInsteadOfVal, UnusedMember, RenamedDefaultParameter, ListToSet)

  //TODO Still have to rely on the bad trick to get the string

  /* Filters the Rules according to the specified sets such that  
   * the pos & rules.annotations non-empty if pos is not the empty set
   * and neg & rules.annotations emtpy. 
   * This enables to lazilly ask for all the rules 
   * or to only define the ones we don't want 
   **/
  def filter[T <: Rule](pos: Set[Tag], neg: Set[Tag])(l: Set[T]): Set[T] = {
    val posSet: Set[String] = pos.map(_.tag)
    val negSet: Set[String] = neg.map(_.tag)
    l.filter {
      x =>
        val annot = cm.classSymbol(x.getClass).annotations.filter(a => a.tree.tpe =:= ru.typeOf[Tag]).flatMap(_.tree.children.tail)
        val annotSet: Set[String] = annot.map(y => ru.show(y).toString).map(_.replaceAll("\"", "")).toSet
        (!(annotSet & posSet).isEmpty || posSet.isEmpty) && (annotSet & negSet).isEmpty
    }
  }

  def filterT(pos: Set[Tag], neg: Set[Tag]): Set[Rule] = {
    filter(pos, neg)(rules)
  }

}