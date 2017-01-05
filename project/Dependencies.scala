import sbt._

object Dependencies {
  lazy val akkaVersion = "2.4.16"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"
  lazy val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  lazy val reactiveMongo = "org.reactivemongo" %% "reactivemongo" % "0.12.1"
  lazy val slf4j = "org.slf4j" % "slf4j-simple" % "1.7.22"
}
