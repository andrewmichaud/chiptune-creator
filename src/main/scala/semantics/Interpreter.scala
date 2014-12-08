package com.andrewmichaud.chiptune

import scala.collection.mutable.Map

import com.andrewmichaud.midi.note._
import com.andrewmichaud.midi.sugar._

import com.andrewmichaud.chiptune.ir._

package object semantics {

  // Tempo. Defaults to 120
  var tempo = 120

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
      var newValue = scala.collection.mutable.ListBuffer[Element]()

      // Strip whitespace
      val tlabel = label.trim

      // Get tokens from value string.
      var tokens = value.trim.split("""\s""").toList
      println(s"Tokens: $tokens \n")

      if (tokens.length < 1) {
        return "Please provide a value to assign."
      }

      for (token <- tokens) {
          println(s"one token is $token")

        // Tokens must be previously defined to be valid OR a valid primitive.
        if (!(table contains token)) {

          // If it isn't in the table, it might be composed of primitives.  It could be a note or
          // chord.
          // Chords must be separated by |, so handle that first.
          val pieces = token.split("""[|]""")

          println("Notes in token:")
          for (piece <- pieces) {
            println(s" note: $piece")
          }

          // This is a single note.
          if (pieces.length < 2) {

            // Create note.
            val notepieces = token.split("""[\W_]""")

            if (table contains notepieces(0)) {
              newValue += Note(notepieces(0), notepieces(1))
            } else {
              return s"${notepieces(0)} is not in the table and $token is invalid."
            }

          // This is a chord.
          } else {

            // Create all notes in chord, if they are valid.
            var notes = scala.collection.mutable.ListBuffer[Note]()
            for (piece <- pieces) {
              val notepieces = piece.split("""[\W_]""")

              if (table contains notepieces(0)) {
                notes += Note(notepieces(0), notepieces(1))

              } else {
                return s"${notepieces(0)} is not in the table, so $piece is invalid and so is $token"
              }
            }

            // Stitch notes into chord.
            val chord = Chord(notes.toList)

            newValue += chord
          }

        } else {

          // If token is defined, prepend to list.  All values are defined backwards because prepending is fast.
          val tokenVal = table get token get

          for (element <- tokenVal) {
            newValue += element
          }
        }
      }

      // If we got here, all tokens were valid.  Add this new value into our map.
      table = table + (label -> newValue.toList)
      return s"Set $label to $newValue."
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
          gen.setTempo(tempo)

          // Synthesize elements.
          val song = (table get label get)
          for (thing <- song) {
            gen.play(thing)
          }

          return "MIDI playing complete"

        } else {
          // TODO throw an error probably.
          return "Label doesn't exist!"
        }

      } catch {
        case e: Exception => println("Exception: " + e.printStackTrace())
        return "MIDI playing failed"
      }
    }

    case Tempo(value) => {

      // Set tempo.
      // TODO bounds check?
      try {
        tempo = value.toInt
        return s"Tempo set to $value"
      } catch {
        case e:Exception => {
          return s"Failed to set tempo, $value invalid tempo."
        }
      }
    }
  }
}
