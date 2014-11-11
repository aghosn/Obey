package scala.obey.utils

import scala.obey.tools.Utils._

object OptionHolder {

	private var pos: Set[Tag] = Set() 
	private var neg: Set[Tag] = Set()

	def addPos(l: Set[Tag]) = pos++= l

	def addNeg(l: Set[Tag]) = neg++= l 
}