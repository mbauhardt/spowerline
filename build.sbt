import AssemblyKeys._

assemblySettings

jarName in assembly := "spowerline.jar"

name := "SPowerline"

version := "0.1"

scalaVersion := "2.11.1"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"