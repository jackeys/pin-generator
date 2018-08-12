package ca.paramnesia.pingenerator.pin

class StepFilter: Filter {
    override fun verify(pin: PIN): Boolean {
        // There is no such thing as a step pattern with 2 or fewer digits
        if (pin.digits.size <= 2) { return true }

        // Since addition and subtraction are circular for digits, this accounts for ascending and descending
        val stepCounts = HashMap<Int, Int>(10)
        for (i in 0..9) { stepCounts[i] = 0 }

        pin.digits.windowed(2).forEach { (previous, current) ->
            stepCounts.forEach { (step, count) ->
                stepCounts[step] = if (current == previous + step) {
                    count + 1
                } else {
                    0
                }
            }
        }

        stepCounts.forEach { (_, count) ->
            if (count == pin.digits.size - 1) { return false }
        }

        return true
    }
}
