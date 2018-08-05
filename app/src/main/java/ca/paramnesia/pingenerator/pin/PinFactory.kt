package ca.paramnesia.pingenerator.pin

import java.util.*

fun ClosedRange<Int>.random() =
        Random().nextInt((endInclusive + 1) - start) +  start

class PinFactory {
    fun generatePin(length: Int) : PIN {
        if (length <= 0) throw IllegalArgumentException("Cannot generate a pin of length $length")

        return PIN(List(length) { Digit((0..9).random()) })
    }
}