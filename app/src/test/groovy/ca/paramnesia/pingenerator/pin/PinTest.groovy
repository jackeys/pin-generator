package ca.paramnesia.pingenerator.pin

import spock.lang.Specification
import spock.lang.Unroll

class PinTest extends Specification {
    @Unroll
    def "PIN #expected_pin prints correctly"() {
        when: "Creating a PIN with a list of digits"
        def pin = new PIN(digits.collect { num -> new Digit(num) })

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
