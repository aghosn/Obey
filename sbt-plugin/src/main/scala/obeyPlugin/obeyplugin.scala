import sbt._
import Keys._
import complete.DefaultParsers._

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
        (Project extract state).append(Seq(obeyFix := "--", obeyWarn := s, scalacOptions ++= Seq("-Ystop-after:obey")), state))
      state
    }

  lazy val obeyListRules =
    Command.command("obey-list") { state: State =>
      Project.evaluateTask(Keys.compile in Compile,
        (Project extract state).append(Seq(scalacOptions ++= Seq("-Ystop-after:obey", "-P:obey:ListRules")), state))
      state
    }

  val packageToMoodle = InputKey[Unit]("obey-check-def", "Obey checking with default")

  val packageToMoodleTask: Setting[InputTask[Unit]] = packageToMoodle <<= inputTask { (argTask: TaskKey[Seq[String]]) =>
    (argTask) map { (args: Seq[String]) =>
      Def.setting {
        (state: State) =>
          if (args.length == 0) {
            println("No arguments")
            Project.evaluateTask(Keys.compile in Compile, state)
          }
          else if(args.length == 1){
            println("Yo many arguments " + args.length)
            Project.evaluateTask(Keys.compile in Compile,
          (Project extract state).append(Seq(obeyFix := "--", obeyWarn := args.mkString, scalacOptions ++= Seq("-Ystop-after:obey")), state))
          }else {
            println("Wrong number of arguments")
          }
      }.evaluate _
    }
  }

  override lazy val projectSettings: Seq[sbt.Def.Setting[_]] = Seq(
    obeyFix := "",
    obeyWarn := "",
    obeyRules := "",
    packageToMoodleTask,
    commands ++= Seq(obeyCheckCmd, obeyFixCmd, obeyListRules),
    addCompilerPlugin("com.github.aghosn" % "plugin_2.11.2" % "0.1.0-SNAPSHOT"),
    scalacOptions ++= Seq(
      "-P:obey:fix:" + obeyFix.value,
      "-P:obey:warn:" + obeyWarn.value,
      "-P:obey:addRules:" + obeyRules.value).filterNot(x => x.endsWith(":")))
}