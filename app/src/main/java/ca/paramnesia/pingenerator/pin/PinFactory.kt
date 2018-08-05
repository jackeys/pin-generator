package ca.paramnesia.pingenerator.pin

import java.security.SecureRandom

fun SecureRandom.nextDigit() = Digit(this.nextInt(10))

class PinFactory {
    fun generatePin(length: Int) : PIN {
        if (length <= 0) throw IllegalArgumentException("Cannot generate a pin of length $length")

        val rng = SecureRandom()
        return PIN(List(length) { rng.nextDigit() })
    }
}