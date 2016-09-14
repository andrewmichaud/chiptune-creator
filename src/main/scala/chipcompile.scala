
package com.andrewmichaud.chiptune

/**
 * Andrew Michaud
 *
 * Chiptune compiler.  Separated from interpreter for simplicity.
 *
 */

import scala.tools.nsc.EvalLoop
import com.andrewmichaud.chiptune.parser.ChipParser
import com.andrewmichaud.chiptune.semantics.eval

object ChiptuneCompiler extends EvalLoop with App {
  override def prompt = ""

  loop { line =>

    // Attempt to load file.

    try {
      val lines = io.Source.fromFile(line).getLines().toList

      // Parse and apply each line.
      for (line <- lines) {
        ChipParser(line) match {
          case ChipParser.Success(t, _) => eval(t)
          case e: ChipParser.NoSuccess => println(e)
        }
      }
    } catch {
      case fnf: java.io.FileNotFoundException => {
        println("File not found!")
        println("Specify a valid file found in the project root directory.")
      }
      case e: Exception => println(e)
    }
  }
}
