/**
 * 	Models for a Rule.
 * 	@author Adrien Ghosn
 */
package scala.obey.model

import tqlscalameta.ScalaMetaTraverser._

trait Rule {
  import scala.obey.model.utils._
  /* Identifier to pretty print and identity the rule
   */
  val description: String

  def apply: Matcher[List[Message]]

  override def toString: String = s"rule $description"
  
}
