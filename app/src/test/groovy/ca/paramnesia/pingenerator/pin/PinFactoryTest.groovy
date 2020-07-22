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
        def pin = new PinFactory(new StepFilter(), randomMock).generatePin(length)

        then: "The PIN should match the sequence up to our length"
        pin.digits == sequence[0..<length]

        where:
        length << [1, 4, 8, 12]
    }

    def "PINs that fail filter aren't returned"() {
        setup: "A known sequence of values for three PINs"
        def failedPIN1 = [1, 1, 1, 1]
        def failedPIN2 = [1, 2, 3, 4]
        def passedPIN = [6, 3, 7, 7]
        Random randomMock = Mock()
        randomMock.nextInt(_) >>> failedPIN1 + failedPIN2 + passedPIN

        and: "A filter that will fail the first two PINs"
        Filter filterMock = Mock()
        filterMock.verify(_) >>> [false, false, true]

        when: "The first two PINs fail the filter"
        def pin = new PinFactory(filterMock, randomMock).generatePin(4)

        then: "The PIN should match the passed PIN"
        pin.digits == passedPIN
    }
}
