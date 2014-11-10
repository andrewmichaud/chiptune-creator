## Summary

This is a short summary of what this project hopes to accomplish, for my
reviewers.

This project involves three pieces.

First, I want to be able to generate music with Scala.  This will involve taking
some existing library in Scala/Java and providing a custom interface to it to
make song creation easy.

Second, I will create a custom syntax and parser for an external DSL to create
music.  The DSl will be written in a plain text file and parsed by Scala code.
It will interface with the Scala music generation code mentioned above, and
hopefully that generation code will do most of the work.

Third, I will create a visualizer for my DSL.  I will try to find some existing
library well-suited to creating musical staves, or something that can be
modified to do it.  I might choose a different visualization, but I think a
visualizer that produces something like sheet music will be the most useful.
I'll work on this alongside the DSL so I can easily tell how my parser is
working and so I have feedback when trying to replicate existing music.

I expect to complete the first part without too much time spent, and I would
like to make good progress on the second and third parts before the end of the
semester.

