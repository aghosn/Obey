package obeyPlugin 

import sbt._
import Keys._

object ObeyPlugin extends sbt.AutoPlugin {
  val obeyFormatPos = settingKey[Seq[String]]("List of tags for rules to use for format.")
  val obeyFormatNeg = settingKey[Seq[String]]("List of tags for rules to avoid for format.")
  val obeyWarnPos = settingKey[Seq[String]]("List of tags for rules to use for report.")
  val obeyWarnNeg = settingKey[Seq[String]]("List of tags for rules to avoid for report.")
  val obeyRules = settingKey[Seq[String]]("Path to .class defined by the user.")

  lazy val obeySettings: Seq[sbt.Def.Setting[_]] = Seq(
    obeyFormatPos := Seq.empty,
    obeyFormatNeg := Seq.empty,
    obeyWarnPos := Seq.empty,
    obeyWarnNeg := Seq.empty,
    obeyRules := Seq.empty,

    scalacOptions ++= Seq("-Xplugin:/home/aghosn/.ivy2/local/org.obey/plugin_2.11.2/0.1.0-SNAPSHOT/jars/plugin_2.11.2.jar")
    ) ++ inScope(Global)(Seq(
      derive(scalacOptions ++= obeyFormatPos.value.distinct map (w => s"-P:obey:format:+${w}")),
      derive(scalacOptions ++= obeyFormatNeg.value.distinct map (w => s"-P:obey:format:-${w}")),
      derive(scalacOptions ++= obeyWarnPos.value.distinct filterNot (obeyFormatPos.value contains _) map (w => s"-P:obey:warn:+${w}")),
      derive(scalacOptions ++= obeyWarnNeg.value.distinct map (w => s"-P:obey:warn:-${w}")),
      derive(scalacOptions ++= obeyRules.value.distinct map (w => s"-P:obey:addRules:${w}"))))

  def derive[T](s: Setting[T]): Setting[T] = {
    try {
      Def derive s
    } catch {
      case _: LinkageError =>
        import scala.language.reflectiveCalls
        Def.asInstanceOf[{ def derive[T](setting: Setting[T], allowDynamic: Boolean, filter: Scope => Boolean, trigger: AttributeKey[_] => Boolean, default: Boolean): Setting[T] }]
          .derive(s, false, _ => true, _ => true, false)
    }
  }
}