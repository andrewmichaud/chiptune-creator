/**
 *
 * Case class to simplify choosing notes to play.
 *
 * author Andrew Michaud
 * date 11/12/14
 */

package com.andrewmichaud.midi.note

// Class for musical elements.
abstract class Element {
  val noteRegex = """[A-G][0-9].*""".r

  // Convert from a string to the corresponding
  // time multiplier.
  def timeConvert(x: String): Double = x match {
    case "Sixteenth" => 0.25
    case "16" => 0.25
    case "𝅘𝅥𝅯" => 0.25
    case "Eighth" => 0.5
    case "8" => 0.5
    case "♪" => 0.5
    case "DotEighth" => 0.75
    case ".8" => 0.75
    case "8." => 0.75
    case "Quarter" => 1.0
    case "4" => 1.0
    case "♩" => 1.0
    case "DotQuarter" => 1.5
    case ".4" => 1.5
    case "4." => 1.5
    case "Half" => 2.0
    case "𝅗𝅥" => 2.0
    case "2" => 2.0
    case "DotHalf" => 2.5
    case "2." => 2.5
    case ".2" => 2.5
    case "Whole" => 4.0
    case "1" => 4.0
    case _ => 1.0
  }

  // Convert from a string to the corresponding
  // MIDI tone number.
  def toneConvert(x: String): Int = x match {
    // Build a MIDI value.
    case noteRegex() => {
      var midivalue:Int = 12

      // Add note value to MIDI value.
      val letter:Char = x.head
      midivalue += {
        letter match {
          case 'C' => 0
          case 'D' => 2
          case 'E' => 4
          case 'F' => 5
          case 'G' => 7
          case 'A' => 9
          case 'B' => 11
        }
      }
      println(s"midivalue is $midivalue")

      // Add octave number to MIDI value.
      val octave = (x drop 1 take 1)
      midivalue += (12 * octave.toInt)

      val accidentals = x.drop(2)
      midivalue += {
        accidentals match {
          case "F" => -1
          case "\u266D" => -1
          case "\uD834\uDD2B" => -2
          case "S" => 1
          case "♯" => 1
          case "𝄪" => 2
          case "N" => 0
          case "♮" => 0

          // Otherwise (covers empty case).
          case _ => 0
        }
      }

      midivalue
    }

    // Handles rest case.
    case _ => 0
  }
}

// Single note.
case class Note(tonein: String, timein: String = "Quarter") extends Element {
  val time = timeConvert(timein)
  val tone = toneConvert(tonein)
}

// Single chord.
case class Chord(notes: List[Note]) extends Element
object Chord {
  def apply(notes: Note*) = new Chord(notes.toList)
}

// Some number of musical elements.
case class Section(elements: List[Element]) extends Element
object Section {
  def apply(elements: Element*) = new Section(elements.toList)
}

