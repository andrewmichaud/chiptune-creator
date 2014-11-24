name := "chiptune_creator"

version := "0.9"

scalaVersion := "2.11.2"

fork in run := true

connectInput in run := true

libraryDependencies ++=
  Seq( "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.1",
       "org.scalatest" % "scalatest_2.11" % "2.1.7",
       "org.scala-lang" % "scala-compiler" % scalaVersion.value )
