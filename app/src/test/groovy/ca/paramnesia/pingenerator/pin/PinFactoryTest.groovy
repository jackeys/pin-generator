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
        pin.length == length

        where:
        length << [1, 4, 43]
    }

    def "PINs are generated randomly"() {
        setup: "A known sequence of values"
        def sequence = [4, 7, 1, 3, 9, 8, 3, 4, 2, 9, 1, 3, 7, 5, 6, 9, 2, 3, 6, 7, 4]
        Random randomMock = Mock()
        randomMock.nextInt(_) >>> sequence

        when: "Generating a PIN using our known sequence"
        def pin = new PinFactory(randomMock).generatePin(length)

        then: "The PIN should match the sequence up to our length"
        pin.digits == sequence[0..<length]

        where:
        length << [1, 4, 8, 12]
    }
}
