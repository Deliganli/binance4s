import sbt._

object Dependencies {

  val circe = Seq(
    "io.circe" %% "circe-core"           % Versions.circe,
    "io.circe" %% "circe-generic"        % Versions.circe,
    "io.circe" %% "circe-generic-extras" % Versions.circe,
    "io.circe" %% "circe-parser"         % Versions.circe,
    "io.circe" %% "circe-shapes"         % Versions.circe,
    "io.circe" %% "circe-fs2"            % Versions.circeFs2
  )

  val tsec = Seq(
    "io.github.jmcardon" %% "tsec-mac" % Versions.tsec
  )

  val fs2 = Seq(
    "co.fs2" %% "fs2-core"             % Versions.fs2,
    "co.fs2" %% "fs2-io"               % Versions.fs2,
    "co.fs2" %% "fs2-reactive-streams" % Versions.fs2,
    "co.fs2" %% "fs2-experimental"     % Versions.fs2
  )

  val enumeratum = Seq(
    "com.beachape" %% "enumeratum"       % Versions.enumeratum,
    "com.beachape" %% "enumeratum-circe" % Versions.enumeratumCirce
  )

  val breeze = Seq(
    "org.scalanlp" %% "breeze" % Versions.breeze
    // "org.scalanlp" %% "breeze-natives" % Versions.breeze
  )

  val scalatest = Seq(
    "org.scalactic" %% "scalactic"               % Versions.scalatest,
    "org.scalatest" %% "scalatest"               % Versions.scalatest % Test,
    "org.mockito"   %% "mockito-scala"           % Versions.mockito % Test,
    "org.mockito"   %% "mockito-scala-cats"      % Versions.mockito % Test,
    "org.mockito"   %% "mockito-scala-scalatest" % Versions.mockito % Test
  )

  val http4sClient = Seq(
    "org.http4s" %% "http4s-circe"           % Versions.http4s,
    "org.http4s" %% "http4s-jdk-http-client" % Versions.http4sJdk
  )

  val logback      = "ch.qos.logback"             % "logback-classic" % Versions.logback
  val logging      = "com.typesafe.scala-logging" %% "scala-logging"  % Versions.scalaLogging
  val cats         = "org.typelevel"              %% "cats-core"      % Versions.cats
  val catsEffect   = "org.typelevel"              %% "cats-effect"    % Versions.cats
  val osLib        = "com.lihaoyi"                %% "os-lib"         % Versions.osLib
  val circeConfig  = "io.circe"                   %% "circe-config"   % Versions.circeConfig
  val shapeless    = "com.chuusai"                %% "shapeless"      % Versions.shapeless

}

object Versions {
  val scalatest       = "3.1.0"
  val logback         = "1.2.3"
  val cats            = "2.0.0"
  val osLib           = "0.3.0"
  val circeFs2        = "0.12.0"
  val circe           = "0.12.0"
  val circeConfig     = "0.7.0"
  val tsec            = "0.2.0-M2"
  val fs2             = "2.1.0"
  val enumeratum      = "1.5.15"
  val enumeratumCirce = "1.5.22"
  val scalaLogging    = "3.9.2"
  val breeze          = "1.0"
  val shapeless       = "2.3.3"
  val mockito         = "1.10.4"
  val http4s          = "0.21.0-M6"
  val http4sJdk       = "0.2.0-M4"

}
