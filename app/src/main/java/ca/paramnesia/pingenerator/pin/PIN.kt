package ca.paramnesia.pingenerator.pin

data class PIN(val digits: List<Digit>) {
    override fun toString() = digits.fold("") { string, digit ->
            string + digit.toString()
        }
}
