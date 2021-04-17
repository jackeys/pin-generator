A simple Android app for generating PINs that avoid some simple patterns. It currently only avoids clear number sequences (e.g. 1234, 97531, etc).

This project was intended primarily as an exploration of what writing tests for Kotlin using the Spock testing framework would look like. It is functional, but it does use a naive approach to filtering out PINs that is technically unbounded in the time it takes because it generates another random PIN if a generated PIN fails.
