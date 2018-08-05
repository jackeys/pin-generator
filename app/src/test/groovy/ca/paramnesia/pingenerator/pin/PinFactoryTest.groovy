package ca.paramnesia.pingenerator.pin

import spock.lang.Specification
import spock.lang.Unroll


class PinFactoryTest extends Specification {
    @Unroll
    def "PINs of length #length cannot be generated"() {
        when: "Generating a PIN using a non-positive length"
        new PinFactory().generatePin(length)

        then: "An exception is thrown indicating that the argument is invalid"
        thrown IllegalArgumentException

        where:
        length << [0, -1, -43, -76543]
    }

    def "PINs of valid lengths are generated"() {
        when: "Generating a PIN using a positive length"
        def pin = new PinFactory().generatePin(length)

        then: "The PIN length should match the input lengths"
        pin.digits.size() == length

        where:
        length << [1, 4, 43]
    }
}