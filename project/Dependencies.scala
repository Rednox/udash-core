import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt._

object Dependencies extends Build {

  val versionOfScala = "2.11.8"
  val silencerVersion = "0.3"

  val scalaJsDomVersion = "0.9.0"
  val scalaTagsVersion = "0.5.4"

  val jqueryWrapperVersion = "1.0.0-rc.2"

  val scalatestVersion = "3.0.0-M15"

  val compilerPlugins = Def.setting(Seq(
    "com.github.ghik" % "silencer-plugin" % silencerVersion
  ).map(compilerPlugin))

  val commonDeps = Def.setting(Seq(
    "com.github.ghik" % "silencer-lib" % silencerVersion
  ))

  val commonTestDeps = Def.setting(Seq(
    "org.scalatest" %%% "scalatest" % scalatestVersion
  ).map(_ % Test))

  val coreCrossDeps = Def.setting(Seq(
    "com.lihaoyi" %%% "scalatags" % scalaTagsVersion
  ))

  val coreFrontendDeps = Def.setting(Seq(
    "org.scala-js" %%% "scalajs-dom" % scalaJsDomVersion,
    "io.udash" %%% "udash-jquery" % jqueryWrapperVersion % Test
  ))
}
