package advent_of_code.year_2023.day1

import java.util.stream.Stream

class Trebuchet {
    companion object {

        val digitsInLetters = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )

        fun getFirstAndLastCalibrations(calibrationValues: Stream<String>): MutableList<Int> {
            return this.getFirstAndLastCalibrations(calibrationValues.toList().joinToString("\n"))
        }

        private fun getFirstAndLastCalibrations(calibrationValues: String): MutableList<Int> {
            val combination: MutableList<Int> = mutableListOf()
            calibrationValues.lines().forEach { line ->
                val allDigits: List<Char> = line.toCharArray().filter { it.isDigit() }
                var combinationOfFirstAndLast = "${allDigits.first()}"

                combinationOfFirstAndLast += if (allDigits.size > 1) {
                    "${allDigits.last()}"
                } else {
                    "${allDigits.first()}"
                }
                combination.add(combinationOfFirstAndLast.toInt())
            }
            return combination
        }

         fun getFirstAndLastAppearingDigitsInLetters(
            calibationsValues: String,
        ): MutableList<String> {

            val allCalibrationValues: MutableList<String> = mutableListOf()
            calibationsValues.lines().forEach { line ->
                val digitsPerLine: MutableList<Pair<Int, String>> = mutableListOf()
                digitsInLetters.keys.forEach { digitAsLetter ->
                    val itsAMatch = line.contains(digitAsLetter)
                    if (itsAMatch) {
                        val cursor = line.indexOf(digitAsLetter)
                        val value = line.substring(cursor, cursor + digitAsLetter.length)
                        digitsPerLine.add(Pair(cursor, value))

                    }
                }
                digitsPerLine.sortBy { it.first }
                val firstCalibrationValue = digitsPerLine.first().second
                val lastCalibrationValue = digitsPerLine.last().second
                allCalibrationValues.add("$firstCalibrationValue,$lastCalibrationValue")
            }
            return allCalibrationValues
        }

         fun getValidCalibrationsFromLettersToDigits(calibationsValuesInLetters: String): MutableList<CharSequence> {
            val calibrationsInFullDigits = mutableListOf<CharSequence>()

            calibationsValuesInLetters.lines().forEach { line ->
                val values = line.split(",")
                val firstValue = digitsInLetters[values[0]]
                val lastValue = digitsInLetters[values[1]]

                val valuesInDigits = "$firstValue,$lastValue"
                calibrationsInFullDigits.add(valuesInDigits)
            }
            return calibrationsInFullDigits
        }

        fun readValidCalibrationsPerLines(
            puzzleInput: Stream<String>,
        ): List<Int> {
            val combinationForTheLine: MutableList<Int> = mutableListOf()
            puzzleInput.toList().joinToString("\n").lines().forEach { line ->
                val mixedDigitsAndTheirPositions: MutableSet<Pair<Int, String>> = mutableSetOf()
                digitsInLetters.keys.forEach { digitAsLetter ->
                    val itsAMatch = line.contains(digitAsLetter)
                    if (itsAMatch) {
                        val cursor = line.indexOf(digitAsLetter)
                        val value = line.substring(cursor, cursor + digitAsLetter.length)
                        val valueAsDigit = digitsInLetters[value]
                        mixedDigitsAndTheirPositions.add(Pair(cursor, "$valueAsDigit"))
                    }
                }
                val allDigits: List<Char> = line.toCharArray().filter { it.isDigit() }
                if (allDigits.isNotEmpty()) {
                    val firstDigit = Pair(line.indexOf(allDigits.first()), "${allDigits.first()}")
                    mixedDigitsAndTheirPositions.add(firstDigit)
                    if (allDigits.size > 1) {
                        val lastDigit = Pair(line.lastIndexOf(allDigits.last()), "${allDigits.last()}")
                        mixedDigitsAndTheirPositions.add(lastDigit)
                    } else {
                        val lastDigit = Pair(line.indexOf(allDigits.first()), "${allDigits.first()}")
                        mixedDigitsAndTheirPositions.add(lastDigit)
                    }
                }
                val orderedDigits = mixedDigitsAndTheirPositions.sortedBy { it.first }
                val combinedCalibration = "${orderedDigits.first().second}${orderedDigits.last().second}"

                combinationForTheLine.add(combinedCalibration.toInt())
            }
            return combinationForTheLine.toList()
        }

    }
}
