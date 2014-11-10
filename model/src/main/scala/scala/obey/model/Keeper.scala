package scala.obey.model
import scala.reflect.runtime.{ universe => ru }
import scala.reflect.runtime.{ currentMirror => cm }
import scala.obey.Rules._
import scala.obey.Tools.Enrichment._

object Keeper {
  val warners: List[RuleWarning] = List(VarInsteadOfVal, UnusedMember, RenamedDefaultParameter)
  val errs: List[RuleError] = Nil
  val formatters: List[RuleFormat] = Nil

  def filter[T <: Rule](pos: Set[Tag], neg: Set[Tag])(l: List[T]): List[T] = {
    l.filter {
      x =>
        val annot = cm.classSymbol(x.getClass).annotations 
        val trees = annot.filter(a => a.tree.tpe == ru.typeOf[Tag]).flatMap(_.tree.children.tail)
        val c: Set[String] = trees.map(y => ru.show(y).toString).map(_.replaceAll("\"", "")).toSet

        pos.map(_.tag).exists(s => c.exists(ss => s.equals(ss))) && !neg.map(_.tag).exists(s => c.exists(ss => s.equals(ss)))
    }
  }

  def filterT(pos: Set[Tag], neg: Set[Tag]): FilterResult = {
    val l1 = filter(pos, neg)(warners)
    val l2 = filter(pos, neg)(errs)
    val l3 = filter(pos, neg)(formatters)
    FilterResult(l1, l2, l3)
  }
}