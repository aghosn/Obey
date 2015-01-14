/*package scala.obey.tools 

import scala.meta.internal.ast._

class Example(global: scala.tools.nsc.Global) {
  implicit val host = scala.meta.internal.hosts.scalac.Scalahost(global)

  def example(sources: List[Source]): Unit = {
    sources.foreach(src => {
      import scala.language.reflectiveCalls
      println(s"THE HEAD ${src.stats.head}")
      val someSubtree = src.stats.head.asInstanceOf[Pkg].stats.head.asInstanceOf[Pkg].stats.head
      println(someSubtree)
      val scratchpads = someSubtree.asInstanceOf[{ def internalScratchpads: Map[_, _] }].internalScratchpads
      val associatedGtree = scratchpads.values.toList.head.asInstanceOf[List[_]].collect { case gtree: global.Tree => gtree }.head
      println(associatedGtree.pos.asInstanceOf[scala.reflect.internal.util.Position])
    })
  }
}
*/