resolvers ++= Seq(
//      "ibiblio" at "http://mirrors.ibiblio.org/pub/mirrors/maven2/",
      "twitter.com" at "http://maven.twttr.com/"
//      "powermock-api" at "http://powermock.googlecode.com/svn/repo/",
//      "scala-tools.org" at "http://scala-tools.org/repo-releases/",
//      "sonatype" at "https://oss.sonatype.org/content/groups/scala-tools/",
//      "testing.scala-tools.org" at "http://scala-tools.org/repo-releases/testing/",
//      "oauth.net" at "http://oauth.googlecode.com/svn/code/maven",
//      "download.java.net" at "http://download.java.net/maven/2/",
//      "atlassian" at "https://m2proxy.atlassian.com/repository/public/",
      // for netty:
//      "jboss" at "http://repository.jboss.org/nexus/content/groups/public/"
    )

addSbtPlugin("com.twitter" % "sbt-package-dist" % "1.0.6")