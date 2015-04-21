name := "redis_msg"

version := "1.0-SNAPSHOT"

javacOptions += "-Xlint:deprecation"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.typesafe" %% "play-plugins-redis" % "2.1.1"
)

resolvers += "org.sedis" at "http://pk11-scratch.googlecode.com/svn/trunk"

play.Project.playJavaSettings