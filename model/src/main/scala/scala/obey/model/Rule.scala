/**
 * 	Models for a Rule.
 * 	@author Adrien Ghosn
 */
package scala.obey.model

import scala.meta.syntactic.ast._
import scala.reflect.runtime.{ universe => ru }
import scala.obey.Tools.Enrichment._
import tqlscalameta.ScalaMetaTraverser._

trait Rule {
  import scala.obey.Tools.Wrapper._
  /* Identifier to pretty print and identity the rule*/
  val name: String

  def report(t: Tree): List[Warning]
  def abort(t: Tree): Unit 
  def format(t: Tree): Unit

}
