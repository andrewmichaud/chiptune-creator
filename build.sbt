name := "chiptune_creator"

version := "0.9"

scalaVersion := "2.11.8"

fork in run := true

connectInput in run := true

libraryDependencies ++=
  Seq( "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3",
       "org.scalatest" % "scalatest_2.11" % "3.0.0",
       "org.scala-lang" % "scala-compiler" % scalaVersion.value )
