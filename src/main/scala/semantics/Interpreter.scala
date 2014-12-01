package com.andrewmichaud.chiptune

import scala.collection.mutable.Map

import com.andrewmichaud.midi.note._
import com.andrewmichaud.midi.sugar._

import com.andrewmichaud.chiptune.ir._

package object semantics {

  // Table of things we know about.
  var table:Map[String,List[Element]] = Map()

  // Prefill all notes and other useful things.
  // We'll give the notes a value but it's not one that we'll end up using.
  // There's almost certainly a better way to do this, but whatever.
  table = table + ("D2" -> List(Note("D2")))
  table = table + ("D2S" -> List(Note("D2S")))
  table = table + ("E2F" -> List(Note("E2F")))
  table = table + ("E2" -> List(Note("E2")))
  table = table + ("F2" -> List(Note("F2")))
  table = table + ("F2S" -> List(Note("F2S")))
  table = table + ("G2F" -> List(Note("G2F")))
  table = table + ("G2" -> List(Note("G2")))
  table = table + ("G2S" -> List(Note("G2S")))
  table = table + ("A2F" -> List(Note("A2F")))
  table = table + ("A2" -> List(Note("A2")))
  table = table + ("A2S" -> List(Note("A2S")))
  table = table + ("B2F" -> List(Note("B2F")))
  table = table + ("B2" -> List(Note("B2")))
  table = table + ("C3" -> List(Note("C3")))
  table = table + ("C3S" -> List(Note("C3S")))
  table = table + ("D3F" -> List(Note("D3F")))
  table = table + ("D3" -> List(Note("D3")))
  table = table + ("D3S" -> List(Note("D3S")))
  table = table + ("E3F" -> List(Note("E3F")))
  table = table + ("E3" -> List(Note("E3")))
  table = table + ("F3" -> List(Note("F3")))
  table = table + ("F3S" -> List(Note("F3S")))
  table = table + ("G3F" -> List(Note("G3F")))
  table = table + ("G3" -> List(Note("G3")))
  table = table + ("G3S" -> List(Note("G3S")))
  table = table + ("A3F" -> List(Note("A3F")))
  table = table + ("A3" -> List(Note("A3")))
  table = table + ("A3S" -> List(Note("A3S")))
  table = table + ("B3F" -> List(Note("B3F")))
  table = table + ("B3" -> List(Note("B3")))
  table = table + ("C4" -> List(Note("C4")))
  table = table + ("C4S" -> List(Note("C4S")))
  table = table + ("D4F" -> List(Note("D4F")))
  table = table + ("D4" -> List(Note("D4")))
  table = table + ("D4S" -> List(Note("D4S")))
  table = table + ("E4F" -> List(Note("E4F")))
  table = table + ("E4" -> List(Note("E4")))
  table = table + ("F4" -> List(Note("F4")))
  table = table + ("F4S" -> List(Note("F4S")))
  table = table + ("G4F" -> List(Note("G4F")))
  table = table + ("G4" -> List(Note("G4")))
  table = table + ("G4S" -> List(Note("G4S")))
  table = table + ("A4F" -> List(Note("A4F")))
  table = table + ("A4" -> List(Note("A4")))
  table = table + ("A4S" -> List(Note("A4S")))
  table = table + ("B4F" -> List(Note("B4F")))
  table = table + ("B4" -> List(Note("B4")))
  table = table + ("C5" -> List(Note("C5")))
  table = table + ("C5S" -> List(Note("C5S")))
  table = table + ("D5F" -> List(Note("D5F")))
  table = table + ("D5" -> List(Note("D5")))
  table = table + ("E5F" -> List(Note("E5F")))
  table = table + ("D5S" -> List(Note("D5S")))
  table = table + ("E5" -> List(Note("E5")))
  table = table + ("F5" -> List(Note("F5")))

  def eval(ast: AST):Any = ast match {
    case Let(label, value) => {

      // Create new array for the value.
      var newValue = List[Element]()

      // Strip whitespace
      val tlabel = label.trim
      val tvalue = value.trim

      // Get tokens from value string.
      val tokens = tvalue.split("\\s+")
      println("Tokens:\n")
      for (token <- tokens) {
        println(s"one token is $token")

        // NOTE: I'm forcing note primitives to be of the form A1-Quarter A2S-Half,
        // etc etc.
        // Tokens must be previously defined to be valid OR a valid primitive.
        // TODO make the error message nicer.
        if (!(table contains token)) {

          // See if this is a note primitive - it is if the first part is there.
          // TODO Being this lazy means you could sneak "Half" through as a valid thing,
          // and that's not right.  Deal with that later.
          val pieces = token.split("""\-""")

          print("pieces: ")
          for (piece <- pieces) {
            println(s"piece: $piece")
          }
          if (pieces.length < 2) {

            throw new Exception(s"$token is not defined or composed of defined pieces")

          } else if (!(table contains pieces(0))) {
            // TODO this lets you apply durations to any existing label, which also isn't right.
            throw new Exception(s"$token is not composed of defined pieces")
          }

          // Otherwise, build a note and add it to the list.
          // The note constructor will handle invalid tones or times.
          newValue = Note(pieces(0), pieces(2)) :: newValue
        }

        // If token is defined, prepend to list.  All values are defined backwards because
        // prepending is fast.
        val tokenVal = table get token get

        for (element <- tokenVal) {
          newValue = element :: newValue
        }
      }

      // If we got here, all tokens were valid.  Add this new value into our map.
      table = table + (label -> newValue)
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

          // Synthesize elements.
          val song = Section((table get label get).reverse)
          gen.play(song)

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
