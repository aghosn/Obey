package scala.obey.utils

import scala.obey.tools.Utils._

/** 
 * Change this
*/
object UserOption {

  case class holder(var pos: Set[Tag], var neg: Set[Tag])

  val all: holder = holder(Set(), Set())
  val format: holder = holder(Set(), Set())
  val report: holder = holder(Set(), Set())
  val abort: holder = holder(Set(), Set()) 
}

/*

  The new idea : Rules have a unified interface, we apply all of them and they return (Tree, Warnings)
  Then the User Option will be used to see if we should abort, warn or rewrite the code according to user specified
  options.

*/