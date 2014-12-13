# chiptune
This is a language for writing music, intended for but not limited to "chiptune"
music.

# how
To use it, you will need [sbt](www.scala-sbt.org).  Once you have it,
open a terminal window to the root directory of this project, and run sbt, and
then run at the sbt prompt.  You can either use the interpreter or the compiler.
The interpreter will let you run commands one at a time.  The compiler will take
a filename (the file must be in the root of the project directory) and will run
all commands in that file.

# commands?
You are allowed the following commands:
"tempo n" - Set the tempo to a value, in beats per minute.
"foo = A3-Quarter B2S-8 F1-DotQuarter" - Create a name for a series of notes.
"foo = A3-Half|B3-Half|C3-Half" - Create a name for a series of chords.
"foo = note anote ynote" - Create a name for a series of previously-defined
things.
"foo = baz A3-Half B3-Half|C3-Half|D3-Half" - Any of the above.
"play foo" - Play exactly one previously-defined thing.

These can be entered at the interpreter prompt or put into a file and run.

Sample files mortalkombat and songofhealing are provided and may be run with the
compiler.
