import sbt._
import Keys._

object obeyplugin extends AutoPlugin {
  val obeyFix = settingKey[String]("List of tags to filter rewritting rules.")
  val obeyWarn = settingKey[String]("List of tags to filter warning rules.")
  val obeyRules = settingKey[String]("Path to .class defined by the user.")

  lazy val obeyFixCmd =
    Command.single("obey-fix") { (state: State, s: String) =>
      Project.evaluateTask(Keys.compile in Compile, 
        (Project extract state).append(Seq(obeyFix := s, obeyWarn := "--", scalacOptions ++= Seq("-Ystop-after:obey")), state))
      state
    }

  lazy val obeyCheckCmd = 
    Command.single("obey-check") { (state: State, s: String) => 
      Project.evaluateTask(Keys.compile in Compile, 
        (Project extract state).append(Seq(obeyFix := "--", obeyWarn := s, scalacOptions++= Seq("-Ystop-after:obey")), state))
      state
    }

  override lazy val projectSettings: Seq[sbt.Def.Setting[_]] = Seq(
    obeyFix := "",
    obeyWarn := "",
    obeyRules := "",
    commands ++= Seq(obeyCheckCmd, obeyFixCmd),
    addCompilerPlugin("com.github.aghosn" % "plugin_2.11.2" % "0.1.0-SNAPSHOT"),
    scalacOptions ++= Seq(
      "-P:obey:fix:" + obeyFix.value,
      "-P:obey:warn:" + obeyWarn.value,
      "-P:obey:addRules:" + obeyRules.value).filterNot(x => x.endsWith(":")))
}