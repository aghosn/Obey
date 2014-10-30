import sbt._

object Dependencies {
	import Settings.metaVersion 
	
	def reflect(sv: String) = "org.scala-lang" % "scala-reflect" % sv
  def compiler(sv: String) = "org.scala-lang" % "scala-compiler" % sv
  lazy val paradise ="org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full
  
  lazy val meta = "org.scalameta" % "scalameta_2.11" % "0.1.0-SNAPSHOT"
  lazy val metafoundation = "org.scalameta" %% "scalameta-foundation" % metaVersion
  lazy val interpreter = "org.scalameta" %% "interpreter" % metaVersion
  
  lazy val scalahost = "org.scalameta" % "scalahost_2.11.2" % "0.1.0-SNAPSHOT"

  lazy val tql = "com.github.begeric" % "tqlscalameta_2.11" % "0.1-SNAPSHOT"
  
  lazy val scalatest = "org.scalatest" %% "scalatest" % "2.1.3" % "test"
  lazy val scalacheck = "org.scalacheck" %% "scalacheck" % "1.11.3" % "test"


}