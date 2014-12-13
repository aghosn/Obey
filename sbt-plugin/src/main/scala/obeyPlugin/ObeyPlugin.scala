package obeyplugin
import sbt._
import Keys._


object ObeyPlugin extends AutoPlugin {
  val obeyFix = settingKey[String]("List of tags to filter rewritting rules.")
  val obeyWarn = settingKey[String]("List of tags to filter warning rules.")
  val obeyRules = settingKey[String]("Path to .class defined by the user.")

  lazy val helloCommand = 
    Command.command("hello") { (state: State) => 
      println("HIIIII")
      state
    }

  override lazy val projectSettings: Seq[sbt.Def.Setting[_]] = Seq(
    obeyFix := "",
    obeyWarn := "",
    obeyRules := "",

    addCompilerPlugin("com.github.aghosn" % "plugin_2.11.2" % "0.1.0-SNAPSHOT"),
    scalacOptions ++= Seq(
      "-P:obey:format:" + obeyFix,
      "-P:obey:warn:" + obeyWarn,
      "-P:obey:addRules:" + obeyRules.value).filterNot(x => x.endsWith(":")))
}