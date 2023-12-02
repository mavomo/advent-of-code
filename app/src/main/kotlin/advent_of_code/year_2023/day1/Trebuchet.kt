package advent_of_code.year_2023.day1

import java.util.stream.Stream

class Trebuchet {
    companion object {
        fun getFirstAndLastCalibrations(calibrationValues: Stream<String>): MutableList<Int> {
            return this.getFirstAndLastCalibrations(calibrationValues.toList().joinToString("\n"))
        }

        private fun getFirstAndLastCalibrations(calibrationValues: String): MutableList<Int> {
            val combination: MutableList<Int> = mutableListOf()
            calibrationValues.lines().forEach { line ->
                val allDigits: List<Char> = line.toCharArray().filter { it.isDigit() }
                var combinationOfFirstAndLast = "${allDigits.first()}"

                if (allDigits.size > 1) {
                    val lastValue: Char = allDigits.last()
                    combinationOfFirstAndLast += "$lastValue"
                } else {
                    combinationOfFirstAndLast += "${allDigits.first()}"
                }
                combination.add(combinationOfFirstAndLast.toInt())
            }
            return combination
        }

    }
}
