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
    var tempo: Double = (120.0 / 60.0 / 1000.0)

    def setTempo(t: Int) {
      tempo = (((1.0 * t) / 60.0) / 1000.0)
    }

    // Play a given note with given velocity and on the given channel.
    def playNote(note: Note = C3, velocity: Int = 120, channel: Int = 0, kind: Time = Quarter) {
      // TODO check range on inputs.
      var duration = (kind.value/ tempo).toLong

      channels(channel).noteOn(note.name, velocity)
      Thread.sleep(duration.toLong)
      channels(channel).noteOff(note.name)
    }

    // Play the simple kind of chord, where some number of notes all have the same duration.
    def playChord(notes: Array[Note], velocity: Int = 120, channel: Int = 0, kind: Time = Quarter) {
      var duration = (kind.value / tempo).toLong

      for (note <- notes) {
        channels(channel).noteOn(note.name, velocity)
      }

      Thread.sleep(duration)
      for (note <- notes) {
        channels(channel).noteOff(note.name)
      }
    }
  }
}

