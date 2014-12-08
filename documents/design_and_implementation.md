# Language design and implementation overview

## Language design
- A user writes programs by writing the notes they want to play in a text file.
- The basic computation my language performs is defining sections of notes and
  sections of sections of notes in their song.  I don't believe this has changed
since my original design, but I've made the specification more precise.
- The only data structures in my DSL are variables.  You can define sections of
  musical elements with variable names, and these can nest.  That's it.
- There are no control structures.  I may want to add some very simple looping
  constructs to emulate repeats in sheet music, but those won't be necessary and
they won't be a top priority.
- There is no input for my programs besides the "code" itself.  The output will
  either be music or a MIDI file that can produce music.  The code can also
output a pictorial representation of the music.
- Most errors will come from parsing.  If you use any syntax that isn't defining
  sections of notes, that will be incorrect,a nd the parser will tell you.  If
you use notes that don't exist or other syntax unrecognized by the parser, that
will also be a parse error.  There may also be checks in the MIDI generation of
there are things that won't be caught by the parser.  I believe this is what was
in my original design, but I've just clarified a bit more.
- There will be no tool support.  Programs will be run from the terminal, and
  music or the pictorial representation will be generated.
- I don't believe there are other DSLs for this domain, but I haven't put a lot
  of effort into looking.  I'm having success with my approach and it seems
useful to me, so I'm willing to ignore possible help I could get by examining
other approaches that might exist.

## Language implementation
- I've decided to use an external implementation, as this seemed easier.  I can
  define arbitrary syntax and then parse it from a file.  I have a small
internal implementation as well, as I've added some wrappers to the Java/Scala
MIDI library to make my life a little easier.  I hope I can exploit the internal
implementation to make the external implementation a bit nicer.
- I chose Scala as a host language because I knew I could do parsing in it and
  it has a MIDI library.  The second point was more important, I basically just
needed a language I could produce sounds in easily.
- I haven't really made any significant syntax decisions yet, as I've only just
  started considering the syntax after focusing on the internal implementation
for a while.  I'd like to have a simple syntax, where you just say things like
BAR = A1 A2 A3
BAZ = B1 B2 B3
FOO = BAR BAZ
- Right now, I have a few pieces.  I have musical elements defined in
  MIDINote.scala.  This includes notes, chords, and sections, the last of which
contains other musical elements.  I have MIDISugar.scala, which describes how to
play all of those elements, and sets up the synthesizer.  MIDISugar basically
wraps the Scala MIDI library and removes all the actual work from the equation.
I have a main function I use for testing so I don't have to recompile everything
all the time.  I have some examples in examples.scala that I've used for testing
that everything works.  Lastly, I have the parser and ir and semantics, which
I'm just starting on.  They will handle parsing the code files and passing them
to MIDISugar and MIDINote for actual sound production.  At some point, I will
have some code to generate the pictorial representation as well.

I think it's a pretty good start, and I don't anticipate having to make dramatic
changes anytime soon.
