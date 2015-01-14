import sbt._
import Keys._
import complete.DefaultParsers._

object obeyplugin extends AutoPlugin {
  val obeyFix = settingKey[String]("List of tags to filter rewritting rules.")
  val obeyWarn = settingKey[String]("List of tags to filter warning rules.")
  val obeyRules = settingKey[String]("Path to .class defined by the user.")

  lazy val obeyListRules =
    Command.args("obey-list", "<args>") { (state: State, args) =>
      if(args.isEmpty){
      Project.evaluateTask(Keys.compile in Compile,
        (Project extract state).append(Seq(scalacOptions ++= Seq("-Ystop-after:obey", "-P:obey:ListRules")), state))
      } else {
        Project.evaluateTask(Keys.compile in Compile, 
          (Project extract state).append(Seq(scalacOptions ++= Seq("-Ystop-after:obey", "-P:obey:ListRules:"+args.mkString)), state))
      }
      state
    }

  lazy val obeyCheckCmd =
    Command.args("obey-check", "<args>") { (state: State, args) =>
      if (args.isEmpty) {
        Project.evaluateTask(Keys.compile in Compile,
          (Project extract state).append(Seq(obeyFix := "--", scalacOptions ++= Seq("-Ystop-after:obey")), state))
      } else {
        Project.evaluateTask(Keys.compile in Compile,
          (Project extract state).append(Seq(obeyWarn := args.mkString.replace(",", ";"), obeyFix := "--", scalacOptions ++= Seq("-Ystop-after:obey")), state))
      }
      state
    }

  lazy val obeyFixCmd =
    Command.args("obey-fix", "<args>") { (state: State, args) =>
      if (args.isEmpty) {
        Project.evaluateTask(Keys.compile in Compile,
          (Project extract state).append(Seq(obeyWarn := "--", scalacOptions ++= Seq("-Ystop-after:obey")), state))
      } else {
        Project.evaluateTask(Keys.compile in Compile,
          (Project extract state).append(Seq(obeyFix := args.mkString.replace(",", ";"), obeyWarn := "--", scalacOptions ++= Seq("-Ystop-after:obey")), state))
      }
      state
    }

  override lazy val projectSettings: Seq[sbt.Def.Setting[_]] = Seq(
    obeyFix := "",
    obeyWarn := "",
    obeyRules := "",
    commands ++= Seq(obeyCheckCmd, obeyFixCmd, obeyListRules),
    addCompilerPlugin("com.github.aghosn" % "plugin_2.11.5" % "0.1.0-SNAPSHOT"),
    scalacOptions ++= Seq(
      "-P:obey:fix:" + obeyFix.value,
      "-P:obey:warn:" + obeyWarn.value,
      "-P:obey:addRules:" + obeyRules.value).filterNot(x => x.endsWith(":")))
}