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

  // Convert from a string to the corresponding
  // time multiplier.
  def timeConvert(x: String): Double = x match {
    case "Eighth" => 0.5
    case "Quarter" => 1.0
    case "Half" => 2.0
    case "DotHalf" => 2.5
    case _ => 1.0
  }

  // Convert from a string to the corresponding
  // MIDI tone number.
  def toneConvert(x: String): Int = x match {
    case "D2" => 38
    case "D2S" => 39
    case "E2F" => 39
    case "E2" => 40
    case "F2" => 41
    case "F2S" => 42
    case "G2F" => 42
    case "G2" => 43
    case "G2S" => 44
    case "A2F" => 44
    case "A2" => 45
    case "A2S" => 46
    case "B2F" => 46
    case "B2" => 47
    case "C3" => 48
    case "C3S" => 49
    case "D3F" => 49
    case "D3" => 50
    case "D3S" => 51
    case "E3F" => 51
    case "E3" => 52
    case "F3" => 53
    case "F3S" => 54
    case "G3F" => 54
    case "G3" => 55
    case "G3S" => 56
    case "A3F" => 56
    case "A3" => 57
    case "A3S" => 58
    case "B3F" => 58
    case "B3" => 59
    case "C4" => 60
    case "C4S" => 61
    case "D4F" => 61
    case "D4" => 62
    case "D4S" => 63
    case "E4F" => 63
    case "E4" => 64
    case "F4" => 65
    case "F4S" => 66
    case "G4F" => 66
    case "G4" => 67
    case "G4S" => 68
    case "A4F" => 68
    case "A4" => 69
    case "A4S" => 70
    case "B4F" => 70
    case "B4" => 71
    case "C5" => 72
    case "C5S" => 73
    case "D5F" => 73
    case "D5" => 74
    case "E5F" => 75
    case "D5S" => 75
    case "E5" => 76
    case "F5" => 77
    case _ => 60
  }
}

// Single note.
case class Note(tonein: String, timein: String = "Quarter") extends Element {
  val time = timeConvert(timein)
  val tone = toneConvert(tonein)
}

// Single chord.
case class Chord(notes: Array[Note]) extends Element

// Some number of musical elements.
case class Section(elements: Array[Element]) extends Element

