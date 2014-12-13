package obeyplugin 
import sbt._

object ObeyPlugin extends AutoPlugin {

  object autoImport {
    val obeyFix = obeyplugin.obeyFix
    val obeyWarn = obeyplugin.obeyWarn
    val obeyRules = obeyplugin.obeyRules
  }

  override lazy val projectSettings = obeyplugin.obeySettings
}