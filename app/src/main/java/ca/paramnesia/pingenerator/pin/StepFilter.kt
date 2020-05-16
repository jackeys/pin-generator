package ca.paramnesia.pingenerator.pin

import kotlin.math.max

/**
 * Filters out PINs based on finding patterns in ascending or descending values.
 * This is based on finding the same step sequence (e.g. every digit is 1 higher than the last,
 * like 1234, or every digit is 2 lower than the last, like 9753, etc). Note that digits are
 * considered to "wrap around", so the pattern 8901 would fail in the same way as the pattern 0123.
 *
 * There is a tolerance that depends on the length of the PIN. For example, while the
 * PIN 123 is very insecure, the PIN 123601837949 does not suffer from the same insecurity.
 */
class StepFilter: Filter {
    companion object {
        // If there is only one step, there is no pattern - we need at least two
        private const val minimumSensibleSteps = 2
        private const val maxPercentOfPinThatCanBeASequence = 0.75
    }

    override fun verify(pin: PIN): Boolean {
        if (pin.length <= minimumSensibleSteps) { return true }

        // Generally, longer pins can have more consecutive steps without being insecure
        // Note that n characters in sequence have n-1 steps, hence why we subtract 1
        val consecutiveStepLimit = max(
                (maxPercentOfPinThatCanBeASequence * pin.length).toInt() - 1,
                minimumSensibleSteps
        )

        var lastStep: Int? = null
        var consecutiveSteps = 0

        pin.digits.windowed(2).forEach { (previous, current) ->
            val step = (current - previous).value

            if (lastStep != step) {
                consecutiveSteps = 1
            } else {
                consecutiveSteps += 1
                if (consecutiveSteps >= consecutiveStepLimit) {
                    return false
                }
            }

            lastStep = step
        }

        return true
    }
}
