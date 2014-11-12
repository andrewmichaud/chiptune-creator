/**
 *
 * Synatx-sugary interface to the Scala MIDI library designed to make composition
 * easier.
 *
 * author Andrew Michaud
 * date 11/05/14
 */

import java.io._
import java.util._
import javax.sound.midi._

import sys.process._

import com.andrewmichaud.midi.note._

package com.andrewmichaud.midisugar {

  class MIDISugar {

    // Data members
    var synth = MidiSystem.getSynthesizer()
    var channels = synth.getChannels()

    // Play a given note with given velocity and on the given channel.
    def playNote(note: Note = C3, velocity: Int = 120, channel: Int = 0, duration: Int = 250, factor: Double = 1.0) {
      // TODO check range on inputs.
      var dur = duration * factor

      channels(channel).noteOn(note.name, velocity)
      Thread.sleep(dur.toLong)
      channels(channel).noteOff(note.name)
    }

    // Play the simple kind of chord, where some number of notes all have the same duration.
    def playChord(notes: Array[Note], velocity: Int = 120, channel: Int = 0, duration: Int = 250, factor: Double = 1.0) {
      var dur = duration * factor

      for (note <- notes) {
        channels(channel).noteOn(note.name, velocity)
      }

      Thread.sleep(dur.toLong)
      for (note <- notes) {
        channels(channel).noteOff(note.name)
      }
    }
  }
}

