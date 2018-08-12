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

        // Since addition and subtraction are circular for digits, this accounts for ascending and descending
        val stepCounts = HashMap<Int, Int>(10)
        for (i in 0..9) { stepCounts[i] = 0 }

        // Generally, longer pins can have more consecutive steps without being insecure
        // Note that n characters in sequence have n-1 steps, hence why we subtract 1
        val consecutiveStepLimit = max(
                (maxPercentOfPinThatCanBeASequence * pin.length).toInt() - 1,
                minimumSensibleSteps
        )

        pin.digits.windowed(2).forEach { (previous, current) ->
            stepCounts.forEach { (step, count) ->
                stepCounts[step] = if (current == previous + step) {
                    if (count + 1 >= consecutiveStepLimit) { return false }
                    else { count + 1 }
                } else {
                    0
                }
            }
        }

        return true
    }
}
