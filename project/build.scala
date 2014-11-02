import sbt._ 
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

import complete.DefaultParsers._

object build extends Build {
	import Dependencies._ 
	import Settings._

	lazy val commonDependencies = Seq(
		libraryDependencies <++= (scalaVersion)(sv => Seq(
			reflect(sv) % "provided",
			compiler(sv) % "provided"
		)), 
		addCompilerPlugin(paradise)
		//addCompilerPlugin(scalahost)
	)

	lazy val plugin = Project(
		id = "plugin", 
		base = file("plugin"), 
		settings = sharedSettings ++ commonDependencies ++ List(
			libraryDependencies ++= Seq(Dependencies.scalahost, Dependencies.scalatest)
		)
	) dependsOn(model)

	lazy val model = Project(
		id = "model", 
		base = file("model"), 
		settings = sharedSettings ++ commonDependencies ++ List(
			libraryDependencies ++= Seq(Dependencies.scalahost, Dependencies.tql)
		)
	)
	
	lazy val tests = Project(
		id = "tests", 
		base = file("tests"), 
		settings = sharedSettings ++ commonDependencies ++ List(
			libraryDependencies ++= Seq(Dependencies.scalahost, Dependencies.scalatest, Dependencies.tql)
		) ++ exposeClasspaths("tests")
	) dependsOn(plugin, model) 
}