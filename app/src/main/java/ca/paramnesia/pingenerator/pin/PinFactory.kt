package ca.paramnesia.pingenerator.pin

import java.security.SecureRandom
import java.util.Random

fun Random.nextDigit() = Digit(this.nextInt(10))

class PinFactory(private val random: Random = SecureRandom()) {
    fun generatePin(length: Int) : PIN {
        if (length <= 0) throw IllegalArgumentException("Cannot generate a pin of length $length")

        return PIN(List(length) { random.nextDigit() })
    }
}