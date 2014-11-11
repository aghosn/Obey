package scala.obey.model

import scala.reflect.runtime.{ universe => ru }
import scala.reflect.runtime.{ currentMirror => cm }
import scala.obey.rules._
import scala.obey.tools.Enrichment._
import scala.obey.tools.Utils._

object Keeper {
  val rules: List[Rule] = List(VarInsteadOfVal, UnusedMember, RenamedDefaultParameter)

  //TODO can now change the comparison since we have case class for Tag
  def filter[T <: Rule](pos: Set[Tag], neg: Set[Tag])(l: List[T]): List[T] = {
    l.filter {
      x =>
        val annot = cm.classSymbol(x.getClass).annotations
        val trees = annot.filter(a => a.tree.tpe == ru.typeOf[Tag]).flatMap(_.tree.children.tail)
        val c: Set[String] = trees.map(y => ru.show(y).toString).map(_.replaceAll("\"", "")).toSet

        pos.map(_.tag).exists(s => c.exists(ss => s.equals(ss))) && !neg.map(_.tag).exists(s => c.exists(ss => s.equals(ss)))
    }
  }

  def filterT(pos: Set[Tag], neg: Set[Tag]): List[Rule] = {
    filter(pos, neg)(rules)
  }
}