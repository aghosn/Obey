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
	)

	lazy val plugin = Project(
		id = "plugin", 
		base = file("plugin"), 
		settings = sharedSettings ++ commonDependencies ++ List(
			libraryDependencies += Dependencies.scalahost
		)
	)

	lazy val tests = Project(
		id = "tests", 
		base = file("tests"), 
		settings = sharedSettings ++ commonDependencies ++ List(
			libraryDependencies += Dependencies.scalahost
		) ++ exposeClasspaths("tests")
	) dependsOn(plugin)
}