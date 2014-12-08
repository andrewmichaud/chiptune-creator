It sounds like you have a pretty good semantic engine up, and all you need now
is the parser/IR!  :D

I'm not at all familiar with the MIDI library, so I don't have any suggestions
about how to play multiple notes at the same time.

Some suggestions about useful freatures:

* "loop pedal" --- some kind of `repeat` function, that takes a sequence
and maybe some kind of `padding` parameter, and plays that sequence
continually with <padding> seconds/milliseconds/whatever between repeats.
Possibly a start time and end time param, like "start playing this on loop here
and stop playing it here", or a `number of repeats` param?
* premade sequences or building blocks.  It looks like a lot of work and syntax
to wade through to compose simple chords and note progression, so it might be
friendlier for the user if you define some things beforehand.  For instance, you
might have each major and minor chord predefined, so that users can just tell
the dsl to play a A-major chord and get a A-major whole note for free, for
instance.

Both of these wouldn't be too hard, I think.  The premade stuff especially is
basically just "write simple examples".  The loop pedal idea migt be a lot more
work, but hopefully it'd just be another semantic or parsing pass, to take the
`repeat` functions and their parameters and replace it with appropriate
insertions into the IR or arguments into the MIDI library.
