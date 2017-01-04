import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "room-watcher-pusher",
    assemblyJarName in assembly := "room-watcher-pusher.jar",
    libraryDependencies += scalaTest % Test
  )
