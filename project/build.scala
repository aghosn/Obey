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
			compiler(sv) % "provided", 
			Dependencies.tql
		)) 
		//addCompilerPlugin(paradise)
		//addCompilerPlugin(scalahost)
	)

	lazy val plugin = Project(
		id = "plugin", 
		base = file("plugin"), 
		settings = sharedSettings ++ commonDependencies ++ List(
			libraryDependencies ++= Seq(Dependencies.scalahost, Dependencies.scalatest),
			resourceDirectory in Compile := baseDirectory.value / "resources"
		)
		
	) dependsOn(model)

	lazy val model = Project(
		id = "model", 
		base = file("model"), 
		settings = sharedSettings ++ commonDependencies ++ List(
			libraryDependencies ++= Seq(Dependencies.scalahost, Dependencies.scalatest)
		)
	)
	
	lazy val tests = Project(
		id = "tests", 
		base = file("tests"), 
		settings = sharedSettings ++ commonDependencies ++ List(
			libraryDependencies ++= Seq(Dependencies.scalahost, Dependencies.scalatest)
		) ++ exposeClasspaths("tests")
	) dependsOn(plugin, model) 
}