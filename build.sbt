organization := "net.mobocracy"

name := "starter"

version := "1.2"

scalaVersion := "2.9.2"

scalacOptions += "-deprecation"

resolvers ++= Seq("sonatype" at "https://oss.sonatype.org/content/groups/scala-tools/",
                  "twitter.com" at "http://maven.twttr.com/")

libraryDependencies ++= Seq(
  "com.twitter" % "finagle-core" % "6.6.2",
  "com.twitter" % "util-core" % "6.5.0",
  "com.twitter" % "finagle-ostrich4" % "6.6.2",
  "org.scala-tools.testing" %% "specs" % "1.6.9",
  "org.jmock" % "jmock-legacy" % "2.5.1" % "test")

