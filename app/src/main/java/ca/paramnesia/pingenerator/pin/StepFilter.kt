package ca.paramnesia.pingenerator.pin

import kotlin.math.max

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
