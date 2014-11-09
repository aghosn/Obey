package scala.obey.utils

import scala.obey.model.Tag

object OptionHolder {

	private var pos: Set[Tag.Value] = Set() 
	private var neg: Set[Tag.Value] = Set()

	def addPos(l: Set[Tag.Value]) = pos++= l

	def addNeg(l: Set[Tag.Value]) = neg++= l 
}