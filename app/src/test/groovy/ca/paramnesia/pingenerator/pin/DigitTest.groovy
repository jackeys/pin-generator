package ca.paramnesia.pingenerator.pin

import spock.lang.Specification
import spock.lang.Unroll

class DigitTest extends Specification {

    @Unroll
    def "#number is not a valid digit"() {
        when: "Creating a digit with an invalid integer"
        new Digit(number)

        then: "An exception is thrown to indicate it's an illegal argument"
        thrown IllegalArgumentException

        where:
        number << [-1, 289180, 10, -234543]
    }

    @Unroll
    def "#number is a valid digit"() {
        when: "Creating a digit with a valid single-digit integer"
        new Digit(number)

        then:
        noExceptionThrown()

        where:
        number << [*0..9]
    }

    def "Digit is printed correctly"() {
        expect: "The digit is printed the same as the integer would be"
        new Digit(number).toString() == number.toString()

        where:
        number << [*0..9]
    }
}

