/**
 *
 * Generating MIDI with Scala
 *
 * author Andrew Michaud
 * converted from code by Karl Brown- automatic-pilot.com/midifile.html
 * date 11/05/14
 */

import java.io._
import java.util._
import javax.sound.midi._

import sys.process._

object MIDIGen {
  // Data members
  // Create MIDI sequence with 24 ticks/beat
  var s = new Sequence(javax.sound.midi.Sequence.PPQ, 24)

  // Obtain MIDI track of sequence.
  var t = s.createTrack()

  // Abstraction functions.  Might deserve their own class or different organization at some
  // point.
  // NOTE tempo argument ignored until I can reverse-engineer setting MIDI tempo.
  def initMIDI(trackname: String, tempo: Int) {
      // General MIDI stuff? -- turn on General MIDI sound set
      // NOTE I hear java doesn't like signed bytes, that might be an issue.
      var b:Array[Byte] = Array(0xF0.toByte, 0x7E, 0x7F, 0x09, 0x01, 0xF7.toByte)
      var sm = new SysexMessage()
      sm.setMessage(b, 6)
      var me = new MidiEvent(sm, 0)
      t.add(me)


      // Set tempo (meta event)
      var mt = new MetaMessage()
      var bt: Array[Byte] = Array(0x02, 0x00, 0x00)
      mt.setMessage(0x51, bt, 3)
      me = new MidiEvent(mt, 0)
      t.add(me)

      // Set track name (meta event)
      mt = new MetaMessage()
      var TrackName = new String(trackname)
      mt.setMessage(0x03, TrackName.getBytes(), TrackName.length())
      me = new MidiEvent(mt, 0)
      t.add(me)

      // Set omni on
      var mm = new ShortMessage()
      mm.setMessage(0xB0, 0x7D, 0x00)
      me = new MidiEvent(mm, 0)
      t.add(me)

      // Set poly on
      mm = new ShortMessage()
      mm.setMessage(0xB0, 0x7F, 0x00)
      me = new MidiEvent(mm, 0)
      t.add(me)
  }

  // Play a given note with given velocity and on the given channel.
  def playNote(note: Note = C3, velocity: Int = 60, channel: Int = 1, duration: Int = 60) {
    // TODO check range on inputs.

    // Note on
    var mm = new ShortMessage()
    mm.setMessage(ShortMessage.NOTE_ON, channel, note.name, velocity)
    var me = new MidiEvent(mm, 1)
    t.add(me)

    // Note off
    mm = new ShortMessage()
    mm.setMessage(ShortMessage.NOTE_OFF, channel, note.name, velocity)
    me = new MidiEvent(mm, duration + 1)
    t.add(me)

  }

  // Incomplete note chart, for transcribing music.
  sealed trait Note { def name: Int }
  case object G2 extends Note { val name = 43 }
  case object G2S extends Note { val name = 44 }
  case object A2F extends Note { val name = 44 }
  case object A2 extends Note { val name = 45 }
  case object A2S extends Note { val name = 46 }
  case object B2F extends Note { val name = 46 }
  case object B2 extends Note { val name = 47 }
  case object C3 extends Note { val name = 48 }
  case object C3S extends Note { val name = 49 }
  case object D3F extends Note { val name = 49 }
  case object D3 extends Note { val name = 50 }
  case object D3S extends Note { val name = 51 }
  case object E3F extends Note { val name = 51 }
  case object E3 extends Note { val name = 52 }
  case object F3 extends Note { val name = 53 }
  case object F3S extends Note { val name = 54 }
  case object G3F extends Note { val name = 54 }
  case object G3 extends Note { val name = 55 }
  case object G3S extends Note { val name = 56 }
  case object A3F extends Note { val name = 56 }
  case object A3 extends Note { val name = 57 }
  case object A3S extends Note { val name = 58 }
  case object B3F extends Note { val name = 58 }
  case object B3 extends Note { val name = 59 }
  case object C4 extends Note { val name = 60 }
  case object C4S extends Note { val name = 61 }
  case object D4F extends Note { val name = 61 }
  case object D4 extends Note { val name = 62 }
  case object D4S extends Note { val name = 63 }
  case object E4F extends Note { val name = 63 }
  case object E4 extends Note { val name = 64 }
  case object F4 extends Note { val name = 65 }
  case object F4S extends Note { val name = 66 }
  case object G4F extends Note { val name = 66 }
  case object G4 extends Note { val name = 67 }
  case object G4S extends Note { val name = 68 }
  case object A4F extends Note { val name = 68 }
  case object A4 extends Note { val name = 69 }
  case object A4S extends Note { val name = 70 }
  case object B4F extends Note { val name = 70 }
  case object B4 extends Note { val name = 71 }
  case object C5 extends Note { val name = 72 }
  case object C5S extends Note { val name = 73 }
  case object D5F extends Note { val name = 73 }
  case object D5 extends Note { val name = 74 }
  case object E5F extends Note { val name = 75 }
  case object D5S extends Note { val name = 75 }
  case object E5 extends Note { val name = 76 }
  case object F5 extends Note { val name = 77 }

  def main(args: Array[String]) {
    println("midifile begin ")

    try {
      initMIDI("test midifile", 5)

      // Set instrument to Piano
      // TODO wrap instruments for cleaner syntax
      // NOTE find out what the hell is happening here so I can make some constants and
      // wrap this function up.
      var mm = new ShortMessage()
      mm.setMessage(0xC0, 0x00, 0x00)
      var me = new MidiEvent(mm, 0)
      t.add(me)

      playNote()
      playNote(note = C3)
      playNote()


      // Set end of track (meta event) 19 ticks later
      var mt = new MetaMessage()
      var bet: Array[Byte] = Array() // empty array
		  mt.setMessage(0x2F, bet, 0)
		  me = new MidiEvent(mt, 140)
		  t.add(me)

      // Write MIDI to MIDI file
      var f = new File("testmidi.mid")
      MidiSystem.write(s, 1, f)

    } catch {
      case e: Exception => println("Exception: " + e.toString())
    }
    println("midifile end ")

    // Create mp3 from midi using external app.
    // TODO make this less dependent on an external app.
    val convertresult = "./midtomp3 testmidi"
  }
}

