import Dependencies._

lazy val versionNumber = "0.2.0"
lazy val snapshot      = "SNAPSHOT"
lazy val versionTag    = versionNumber + "-" + snapshot

lazy val root = project
  .in(file("."))
  .aggregate(common, rest, websocket)
  .disablePlugins(AssemblyPlugin)
  .settings(
    Common.settings,
    Publish.settings,
    name := "binance4s",
    version := versionTag,
    skip in publish := true,
    crossScalaVersions := Nil
  )

lazy val common = project
  .in(file("binance4s-common"))
  .settings(
    Common.settings,
    Publish.settings,
    name := "binance4s-common",
    version := versionTag,
    libraryDependencies ++= Seq(logging, cats, catsEffect) ++ enumeratum ++ circe ++ scalatest ++ http4sClient
  )

lazy val rest = project
  .in(file("binance4s-rest"))
  .dependsOn(common % "compile->compile;test->test")
  .settings(
    Common.settings,
    Publish.settings,
    name := "binance4s-rest",
    version := versionTag,
    libraryDependencies ++= Seq(circeConfig) ++ tsec
  )

lazy val websocket = project
  .in(file("binance4s-websocket"))
  .dependsOn(common % "compile->compile;test->test")
  .settings(
    Common.settings,
    Publish.settings,
    name := "binance4s-websocket",
    version := versionTag,
    libraryDependencies ++= fs2
  )
