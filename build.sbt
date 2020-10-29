name := "Battleshipwebtech"
 
version := "1.0" 
      
lazy val `battleshipwebtech` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(jdbc, ehcache, ws, specs2 % Test, guice)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

libraryDependencies += guice

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

libraryDependencies += "com.h2database" % "h2" % "1.4.196"

libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.3"

libraryDependencies += "com.google.inject" % "guice" % "3.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.1.1"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"

      