package ca.paramnesia.pingenerator.pin

import kotlin.math.max
import kotlin.math.min

class StepFilter: Filter {
    override fun verify(pin: PIN): Boolean {
        // There is no such thing as a step pattern with 2 or fewer digits
        if (pin.digits.size <= 2) { return true }

        // Since addition and subtraction are circular for digits, this accounts for ascending and descending
        val stepCounts = HashMap<Int, Int>(10)
        for (i in 0..9) { stepCounts[i] = 0 }

        // If an entire PIN is nothing but the same step, it should fail regardless of size
        // Generally, though, longer pins can have more consecutive steps without being insecure
        // Note that n characters in sequence have n-1 steps, hence why we subtract 1
        val consecutiveStepLimit = min(pin.digits.size, max(3, 3 * pin.digits.size / 4)) - 1

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
