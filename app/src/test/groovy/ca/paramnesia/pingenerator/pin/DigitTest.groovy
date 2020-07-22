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

    @Unroll
    def "Digits #digit1 + #digit2 = #sum"() {
        expect: "Digits add together like numbers, wrapping around if exceeding 9"
        new Digit(digit1) + new Digit(digit2) == sum

        where:
        digit1 | digit2 || sum
        1      | 1      || 2
        5      | 3      || 8
        6      | 8      || 4
        4      | 0      || 4
    }

    @Unroll
    def "Digits #digit1 - #digit2 = #difference"() {
        expect: "Digits add together like numbers, wrapping around if exceeding 9"
        new Digit(digit1) - new Digit(digit2) == difference

        where:
        digit1 | digit2 || difference
        1      | 1      || 0
        5      | 3      || 2
        6      | 8      || 8
        4      | 0      || 4
    }
}

