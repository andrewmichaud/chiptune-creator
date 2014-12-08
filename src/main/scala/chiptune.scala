package com.andrewmichaud.chiptune

/**
 * Andrew Michaud
 *
 * 11/24/14
 *
 * Main eval loop, where all the magic happens.
 *
 */

import scala.tools.nsc.EvalLoop
import com.andrewmichaud.chiptune.parser.ChipParser
import com.andrewmichaud.chiptune.semantics.eval

object ChiptuneCreator extends EvalLoop with App {
  override def prompt = ""

  loop { line =>

    // Switch between interpreting and parsing modes.
    line match {
      case "Interpreter" => {
        ChipParser(line) match {
          case ChipParser.Success(t,_) => println(eval(t))
          case e: ChipParser.NoSuccess => println(e)
        }
      }

      case "Compiler" => {
        println("Please specify file name.  Assumes project root directory.")

        try {
          val lines = io.Source.fromFile(line).getLines().toList
          // Parse and run each line.

          for (line <- lines) {
            ChipParser(line) match {
              case ChipParser.Success(t,_) => println(eval(t))
              case e: ChipParser.NoSuccess => println(e)
            }
          }
        } catch {
          case e: Exception => println(e)
        }
      }

      case _ => {
        println("Please specify either Interpreter or Compiler.")
      }
    }

  }
}
