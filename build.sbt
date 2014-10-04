import AssemblyKeys._

assemblySettings

jarName in assembly := "spowerline.jar"

name := "SPowerline"

version := "0.1"

scalaVersion := "2.11.1"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.6"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"