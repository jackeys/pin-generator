package ca.paramnesia.pingenerator.pin

data class PIN(val digits: List<Digit>) {
    companion object {
        fun from(digits: List<Int>) = PIN(digits.map { Digit(it) })
    }

    val length = digits.size

    override fun toString() = digits.fold("") { string, digit ->
            string + digit.toString()
        }
}
