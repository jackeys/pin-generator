package ca.paramnesia.pingenerator.pin

import spock.lang.Specification

class PinTest extends Specification {
    def "PIN generated correctly from array of integers"() {
        when: "Creating a PIN with a list of single-digit integers"
        def pin = PIN.@Companion.from(digits)

        then: "The list of digits should be the same as the list of integers"
        pin.digits == digits

        where:
        digits << [
                [4, 1, 6, 7, 3, 5, 8],
                [7, 7, 7],
                [0, 1, 5, 7, 3, 8, 5, 3, 2, 4]
        ]
    }

    def "An exception is thrown if trying to create a PIN from non-digit integers"() {
        when: "Creating a PIN with a list of non-digit integers"
        PIN.@Companion.from(numbers)

        then:
        thrown IllegalArgumentException

        where:
        numbers << [
                [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
                [76543456,3, 7, 5],
                [-1, 4, 7, 4, 5],
                [5, 9, 1, 3, 5, -19473933]
        ]
    }

    def "PIN prints correctly"() {
        when: "Creating a PIN with a list of digits"
        def pin = PIN.@Companion.from(digits)

        then: "The digits should be printed naturally, in order"
        pin.toString() == expected_pin

        where:
        digits                || expected_pin
        [1, 2, 3, 4]          || "1234"
        [3, 2]                || "32"
        [4, 5, 2, 1, 7, 9, 4] || "4521794"
        []                    || ""
    }
}
