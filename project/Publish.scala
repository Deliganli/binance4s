import sbt.Keys._
import sbt._
import xerial.sbt.Sonatype.autoImport._

object Publish {

  val deliganli = Developer(
    "deliganli",
    "Sait Sami Kocatas",
    "saitkocatas@gmail.com",
    url("https://github.com/deliganli")
  )

  val scm = ScmInfo(
    url("https://github.com/deliganli/binance4s"),
    "git@github.com:Deliganli/binance4s.git"
  )

  val apacheLicense = (
    "Apache-2.0",
    url("http://www.apache.org/licenses/LICENSE-2.0")
  )

  lazy val settings = Seq(
    organization := "com.deliganli",
    homepage := Some(url("https://github.com/deliganli/binance4s")),
    scmInfo := Some(scm),
    developers := List(deliganli),
    licenses += apacheLicense,
    publishMavenStyle := true,
    credentials += Credentials(Path.userHome / ".sbt" / "sonatype.credential"),
    publishTo := sonatypePublishToBundle.value
  )
}
