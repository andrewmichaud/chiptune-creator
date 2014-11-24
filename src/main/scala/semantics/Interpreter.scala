package com.andrewmichaud.chiptune

import scala.collection.mutable.Map

import com.andrewmichaud.midi.note._
import com.andrewmichaud.midi.sugar._

import com.andrewmichaud.chiptune.ir._

package object semantics {

  var table:Map[String,Element] = Map()

  def eval(ast: AST):Any = ast match {
    case Let(label, value) => {
      // Strip whitespace
      val tlabel = label.trim
      val tvalue = value.trim

      // Get tokens from value string.
      val tokens = tvalue.split("\\s+")
      println("Tokens:\n")
      for (token <- tokens) {
        println(s"  $token")
      }
    }

    case Play(label) => {

      // TODO clean this up.
      // And actually let the user set tempo.
      println("MIDI playing begin")
      var gen = new Sugar

      try {
        // Check if label actually exists.
        if (table contains label) {

          // Open synthesizer and get channels.
          gen.synth.open()
          gen.setTempo(102)

          // lol
          gen.play(table get label get)

          println("MIDI playing complete")

        } else {
          // TODO throw an error probably.
          println("Label doesn't exist!")
        }

      } catch {
        case e: Exception => println("Exception: " + e.printStackTrace())
        println("MIDI playing failed")
      }
    }
  }
}
