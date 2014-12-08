package obeyplugin 

object ObeyPlugin extends sbt.AutoPlugin {
  override def trigger = allRequirements
  object autoImport {
    val obeyFormatPos = obeyplugin.obeyFormatPos
    val obeyFormatNeg = obeyplugin.obeyFormatNeg
    val obeyWarnPos = obeyplugin.obeyWarnPos
    val obeyWarnNeg = obeyplugin.obeyWarnNeg
    val obeyRules = obeyplugin.obeyRules
  }
  override lazy val projectSettings = obeySettings
}