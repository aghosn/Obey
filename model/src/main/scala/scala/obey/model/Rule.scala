/**
 * 	Models for a Rule.
 * 	@author Adrien Ghosn
 */
package scala.obey.model

import scala.meta.tql.ScalaMetaFusionTraverser._

trait Rule {
  import scala.obey.model._
  /* Identifier to pretty print and identity the rule
   */
  def description: String

  def apply: Matcher[List[Message]]

  override def toString = {
    lazy val annots = getAnnotations(this)
    lazy val name = this.getClass.getName.split("\\$").last.split('.').last
    name + "("+description+", Tags:("+annots.mkString(",")+"))"
  }
  
}
