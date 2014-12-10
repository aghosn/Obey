import sbt._
import Keys._

package object obeyplugin {
  val obeyFormatPos = settingKey[Seq[String]]("List of tags for rules to use for format.")
  val obeyFormatNeg = settingKey[Seq[String]]("List of tags for rules to avoid for format.")
  val obeyWarnPos = settingKey[Seq[String]]("List of tags for rules to use for report.")
  val obeyWarnNeg = settingKey[Seq[String]]("List of tags for rules to avoid for report.")
  val obeyRules = settingKey[String]("Path to .class defined by the user.")

  val dep = "com.github.aghosn" % "plugin_2.11.2" % "0.1.0-SNAPSHOT"

  lazy val obeySettings: Seq[sbt.Def.Setting[_]] = Seq(
    obeyFormatPos := Seq.empty,
    obeyFormatNeg := Seq.empty,
    obeyWarnPos := Seq.empty,
    obeyWarnNeg := Seq.empty,
    obeyRules := "",

    addCompilerPlugin("com.github.aghosn" % "plugin_2.11.2" % "0.1.0-SNAPSHOT"),
    scalacOptions ++= Seq(
      "-P:obey:format:" + obeyFormatPos.value.map("+" + _).mkString + obeyFormatNeg.value.map("-" + _).mkString,
      "-P:obey:warn:" + obeyWarnPos.value.filterNot(x => obeyFormatPos.value.contains(x)).map("+" + _).mkString + obeyWarnNeg.value.map("-" + _).mkString,
      "-P:obey:addRules:" + obeyRules.value).filterNot(x => x.endsWith(":")))
}