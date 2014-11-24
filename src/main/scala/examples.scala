/*
 * Andrew Michaud
 * Examples of songs in this DSL, in Scala code.
 *
 *
 */

package com.andrewmichaud.midi.examples

import com.andrewmichaud.midi.sugar._
import com.andrewmichaud.midi.note._

// Song of healing
abstract class Songs

case object SongOfHealing {
  val FirstTwo = Section(Note("B3"), Note("A3"))
  val Afirst = Section(FirstTwo, Note("F3"))
  val Aend = Section(FirstTwo, Note("E3", "Eighth"), Note("D3", "Eighth"), Note("E3", "DotHalf"))

  val A = Section(Afirst, Afirst, Aend)

  val NextThree = Section(Note("F3"), Note("C3"), Note("B2"))
  val NextFour = Section(Note("F3"), Note("E3"), Note("B2", "Eighth"), Note("A2", "Eighth"),
                         Note("B2", "DotHalf"))

  val Mid = Section(NextThree, NextThree, NextFour)

  val Transition = Section(Note("F3"), Note("E3"), Chord(Note("B3"), Note("E3")),
                           Chord(Note("E3", "DotHalf"), Note("G3", "DotHalf")))

  val B = Section(NextThree, NextThree, Transition)

  val CfirstChord = Chord(Note("A3"), Note("F3"))
  val CsecondChord = Chord(Note("D4"), Note("A3"))
  val CthirdChord = Chord(Note("G3"), Note("E3"))

  val Cfirst = Section(Iterator.fill(3)(CfirstChord).toList)
  val Csecond = Section(Iterator.fill(3)(CsecondChord).toList)
  val Cthird = Section(Iterator.fill(3)(CthirdChord).toList)

  val Cend = Section(Chord(Note("C4"), Note("E3")),
                     Chord(Note("G3", "Half"), Note("E3", "Half")))

  val C = Section(Cfirst, Csecond, Cthird, Cend)

  val DfirstChord = Chord(Note("F3"), Note("D3"))
  val DsecondChord = Chord(Note("B3F"), Note("F3"))
  val Dfirst = Section(Iterator.fill(3)(DfirstChord).toList)
  val Dsecond = Section(Iterator.fill(3)(DsecondChord).toList)


  val Dend = Section(Chord(Note("E3"), Note("C3")), Chord(Note("C3"), Note("D3")),
                     Chord(Note("A3"), Note("C3")),
                     Chord(Note("E3", "DotHalf"), Note("C3", "DotHalf")))

  val D = Section(Dfirst, Dsecond, Dend)

  val Efirst = Section(Chord(Note("F3"), Note("D3")),
                       Chord(Note("G3"), Note("D3")),
                       Chord(Note("A3"), Note("D3"))
                      )

  val Esecond = Section(Chord(Note("B3F"), Note("F3")),
                        Chord(Note("C4"), Note("F3")),
                        Chord(Note("D4"), Note("F3"))
                        )

  val Ethird = Section(Chord(Note("A3"), Note("D3")),
                       Chord(Note("B3"), Note("D3")),
                       Chord(Note("D4"), Note("D3"))
                      )

  val Eend = Chord(Note("E3", "DotHalf"), Note("E4", "DotHalf"))

  val E = Section(Efirst, Esecond, Ethird, Eend)

  val Song = Section(A, A,  B, C, D, C, E, A, A, B, C, D, C, E, A)
}
