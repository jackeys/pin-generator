package ca.paramnesia.pingenerator.pin

import spock.lang.Specification


class StepFilterTest extends Specification {
    def filter

    def setup() {
        filter = new StepFilter()
    }

    def "PINs without any obvious numeric pattern pass validation"() {
        expect:
        filter.verify(PIN.@Companion.from(digits))

        where:
        digits << [
                [7, 2, 3, 8],
                [9, 5, 1, 3, 0, 6, 8],
                [8, 8, 3, 5, 2, 4, 3, 7, 1, 9 ,5, 4],
                [6, 4, 7],
                [1],
                [1, 4]
        ]
    }

    def "PINs where each digit ascends fail validation"() {
        expect:
        !filter.verify(PIN.@Companion.from(digits))

        where:
        digits << [
                [1, 2, 3],
                [1, 5, 9],
                [2, 4, 6, 8],
                [0, 3, 6, 9],
                [0, 3, 6, 9, 2, 5, 8],
                [5, 0, 5, 0, 5, 0, 5, 0]
        ]
    }

    def "PINs where each digit descends fail validation"() {
        expect:
        !filter.verify(PIN.@Companion.from(digits))

        where:
        digits << [
                [3, 2, 1],
                [9, 7, 5, 3, 1],
                [5, 1, 7, 3, 9],
                [9, 6, 3, 0]
        ]
    }

    def "PINs where each digit is identical fail validation"() {
        expect:
        !filter.verify(PIN.@Companion.from(digits))

        where:
        digits << [
                [1, 1, 1, 1],
                [3, 3, 3],
                [9, 9, 9, 9, 9, 9, 9, 9, 9],
                [7, 7, 7, 7, 7]
        ]
    }
}
