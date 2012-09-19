import com.twitter.sbt._

organization := "net.mobocracy"

name := "starter"

version := "1.1"

scalaVersion := "2.9.1"

resolvers ++= Seq("sonatype" at "https://oss.sonatype.org/content/groups/scala-tools/",
                  "twitter.com" at "http://maven.twttr.com/")

libraryDependencies ++= Seq("com.twitter" % "finagle-core" % "5.3.9",
  "com.twitter" % "util" % "3.0.0" intransitive,
  "com.twitter" % "finagle-ostrich4" % "5.3.8",
  "org.scala-tools.testing" %% "specs" % "1.6.9",
  "org.jmock" % "jmock-legacy" % "2.5.1" % "test")
