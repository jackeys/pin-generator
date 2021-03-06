package ca.paramnesia.pingenerator.pin

data class Digit(val value: Int) : Number(), Comparable<Digit> {
    init {
        if (value !in 0..9) {
            throw IllegalArgumentException("Digits must be between 0 and 9; got $value")
        }
    }

    fun compareTo(other: Int): Int = value.compareTo(other)
    override fun compareTo(other: Digit): Int = value.compareTo(other.value)

    operator fun plus(other: Digit): Digit = Digit((value + other.value) % 10)
    operator fun plus(number: Int): Digit = this + Digit(number)

    operator fun minus(other: Digit): Digit {
        var newValue = (value - other.value) % 10
        if (newValue < 0) newValue += 10

        return Digit(newValue)
    }

    operator fun minus(number: Int): Digit = this - Digit(number)

    override fun toByte(): Byte = value.toByte()
    override fun toChar(): Char = value.toChar()
    override fun toDouble(): Double = value.toDouble()
    override fun toFloat(): Float = value.toFloat()
    override fun toInt(): Int = value
    override fun toLong(): Long = value.toLong()
    override fun toShort(): Short = value.toShort()
    override fun toString() = value.toString()
}