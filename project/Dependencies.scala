import sbt._

object Dependencies {
	import Settings.metaVersion 
	
	def reflect(sv: String) = "org.scala-lang" % "scala-reflect" % sv
  def compiler(sv: String) = "org.scala-lang" % "scala-compiler" % sv
  
  lazy val scalahost = "org.scalameta" % "scalahost_2.11.2" % "0.1.0-SNAPSHOT"

  lazy val meta = "org.scalameta" % "scalameta_2.11" % "0.1.0-SNAPSHOT"
  lazy val tql = "com.github.begeric" % "tqlscalameta_2.11" % "0.1-SNAPSHOT"
  
  lazy val scalatest = "org.scalatest" %% "scalatest" % "2.1.3" % "test"
}