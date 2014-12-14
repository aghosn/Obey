import sbt.Keys._
import sbt._

object build extends Build {
  import Dependencies._
  import PublishSettings._
  import Settings._

  lazy val commonDependencies = Seq(
    libraryDependencies <++= (scalaVersion)(sv => Seq(
      compiler(sv) % "provided",
      Dependencies.tql,
      Dependencies.scalatest)) //addCompilerPlugin(paradise)
      //addCompilerPlugin(scalahost)
      )

  lazy val plugin = Project(
    id = "plugin",
    base = file("plugin"),
    settings = sharedSettings ++ publishableSettings ++ commonDependencies ++ mergeDependencies ++ List(
      libraryDependencies ++= Seq(Dependencies.scalahost),
      resourceDirectory in Compile := baseDirectory.value / "resources")) dependsOn (model)

  lazy val model = Project(
    id = "model",
    base = file("model"),
    settings = sharedSettings ++ publishableSettings ++ commonDependencies)

  lazy val tests = Project(
    id = "tests",
    base = file("tests"),
    settings = sharedSettings ++ commonDependencies ++ exposeClasspaths("tests")) dependsOn (plugin, model)

  lazy val sbtPlug: Project = Project(
    id = "sbt-plugin",
    base = file("sbt-plugin"),
    settings = PublishSettings.publishSettings ++ publishableSettings ++ List(sbtPlugin := true, name := "sbt-obeyplugin"))
}
