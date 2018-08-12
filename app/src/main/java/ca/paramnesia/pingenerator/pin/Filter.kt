package ca.paramnesia.pingenerator.pin

interface Filter {
    fun verify(pin: PIN): Boolean
}
