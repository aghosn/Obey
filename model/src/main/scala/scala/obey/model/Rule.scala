/**
 * 	Models for a Rule.
 * 	@author Adrien Ghosn
 */
package scala.obey.model

import scala.meta.syntactic.ast._
import scala.reflect.runtime.{ universe => ru }
import scala.obey.tools.Enrichment._
import tqlscalameta.ScalaMetaTraverser._

trait Rule {
  import scala.obey.tools.Utils._
  /* Identifier to pretty print and identity the rule
   * usage: "name: description"
   */
  val name: String

  def apply: Matcher[List[Message]]

  override def toString: String = s"rule $name"
  
}
