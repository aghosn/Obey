import sbt._
import Keys._

package object obeyplugin {
  val obeyFix = settingKey[String]("List of tags to filter rewritting rules.")
  val obeyWarn = settingKey[String]("List of tags to filter warning rules.")
  val obeyRules = settingKey[String]("Path to .class defined by the user.")

  lazy val obeySettings: Seq[sbt.Def.Setting[_]] = Seq(
    obeyFix := "",
    obeyWarn := "",
    obeyRules := "",
  
    addCompilerPlugin("com.github.aghosn" % "plugin_2.11.2" % "0.1.0-SNAPSHOT"),
    scalacOptions ++= Seq(
      "-P:obey:format:" + obeyFix.value,
      "-P:obey:warn:" + obeyWarn.value,
      "-P:obey:addRules:" + obeyRules.value).filterNot(x => x.endsWith(":")))
}