import sbt._
import Process._
import com.twitter.sbt._

class StarterProject(info: ProjectInfo) extends StandardServiceProject(info) with DefaultRepos {
  val finagleVersion = "1.8.3"

  val finagleC = "com.twitter" % "finagle-core" % finagleVersion
  val finagleO = "com.twitter" % "finagle-ostrich4" % finagleVersion

  val util = "com.twitter" % "util" % "1.11.2"

  val specs = "org.scala-tools.testing" % "specs_2.8.1" % "1.6.7" % "test" withSources()
  val check = "org.scala-tools.testing" % "scalacheck_2.8.1" % "1.8" % "test"
  val jmock = "org.jmock" % "jmock" % "2.5.1" % "test"
  val jmockLeg = "org.jmock" % "jmock-legacy" % "2.5.1" % "test"
  val hamcrest_all = "org.hamcrest" % "hamcrest-all" % "1.1" % "test"
  val cglib = "cglib" % "cglib" % "2.1_3" % "test"
  val asm = "asm" % "asm" % "1.5.3" % "test"
  val objenesis = "org.objenesis" % "objenesis" % "1.1" % "test"

  override def mainClass = Some("net.mobocracy.starter.Main")
}
