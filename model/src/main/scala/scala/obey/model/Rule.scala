/**
 * 	Models for a Rule.
 * 	@author Adrien Ghosn
 */
package scala.obey.model

import tqlscalameta.ScalaMetaTraverser._

trait Rule {
  import scala.obey.model.utils._
  /* Identifier to pretty print and identity the rule
   * usage: "name: description"
   */
  val name: String

  def apply: Matcher[List[Message]]

  override def toString: String = s"rule $name"
  
}
