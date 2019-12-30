import Dependencies._

lazy val scala212               = "2.12.8"
lazy val scala213               = "2.13.0"
lazy val supportedScalaVersions = List(scala212, scala213)

lazy val root = project
  .in(file("."))
  .aggregate(common, rest, websocket)
  .disablePlugins(AssemblyPlugin, Sonatype)
  .settings(
    commonSettings,
    name := "binance4s",
    skip in publish := true,
    crossScalaVersions := Nil
  )

lazy val common = project
  .in(file("binance4s-common"))
  .settings(
    commonSettings,
    publishSettings,
    name := "binance4s-common",
    libraryDependencies ++= Seq(logging, jodaDateTime, cats, catsEffect) ++ enumeratum ++ circe ++ scalatest ++ http4sClient,
    test in assembly := {},
    crossScalaVersions := supportedScalaVersions
  )

lazy val rest = project
  .in(file("binance4s-rest"))
  .dependsOn(common % "compile->compile;test->test")
  .settings(
    commonSettings,
    publishSettings,
    name := "binance4s-rest",
    libraryDependencies ++= Seq(circeConfig) ++ tsec,
    test in assembly := {},
    crossScalaVersions := supportedScalaVersions
  )

lazy val websocket = project
  .in(file("binance4s-websocket"))
  .dependsOn(common % "compile->compile;test->test")
  .settings(
    commonSettings,
    publishSettings,
    name := "binance4s-websocket",
    libraryDependencies ++= fs2,
    test in assembly := {},
    crossScalaVersions := supportedScalaVersions
  )

// ------------------- Settings -------------------

lazy val commonSettings = Seq(
  organization := "com.deliganli",
  scalaVersion := "2.13.0",
  version := "0.1.0-SNAPSHOT",
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-language:higherKinds",
    "-language:postfixOps",
    "-feature"
  ),
  addCompilerPlugin("org.typelevel" % "kind-projector"      % "0.10.3" cross CrossVersion.binary),
  addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1")
)

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := name.value + ".jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case "application.conf"            => MergeStrategy.concat
  }
)

val deliganli = Developer(
  "deliganli",
  "Sait Sami Kocatas",
  "saitkocatas@gmail.com",
  url("https://github.com/deliganli")
)

lazy val publishSettings = Seq(
  organization := "com.deliganli",
  homepage := Some(url("https://github.com/deliganli/binance4s")),
  scmInfo := Some(ScmInfo(url("https://github.com/deliganli/binance4s"), "git@github.com:Deliganli/binance4s.git")),
  developers := List(deliganli),
  licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
  publishMavenStyle := true,
  credentials += Credentials(Path.userHome / ".sbt" / "sonatype.credential"),
  publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)
)
