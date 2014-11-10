/**
 * 	Models for a Rule.
 * 	@author Adrien Ghosn
 */
package scala.obey.model

import scala.meta.syntactic.ast._
import scala.reflect.runtime.{universe => ru}
import scala.obey.Tools.Enrichment._

/*TODO look at how to extend enums*/
trait Rule {
  /* Identifier to pretty print and identity the rule*/
  val name: String

}

trait Msg {
  val message: String
}

/* Rules that simply generate warnings*/
trait RuleWarning extends Rule { 
  //def warning(t: Tree): Warning

  case class Warning(message: String) extends Msg {
    val rule = this
  }

  def apply(t: Tree): List[Warning]
}

/* Rules that will stop the execution
   TODO enforce stop*/
trait RuleError extends Rule {
  /*TODO change this*/
  def error(t: Tree): Error

  case class Error(message: String) extends Msg {
    val rule = this
    /*TODO maybe exception here or something*/
  }
}

trait RuleFormat extends Rule {
}