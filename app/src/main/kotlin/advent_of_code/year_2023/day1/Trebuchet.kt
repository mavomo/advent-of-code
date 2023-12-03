package advent_of_code.year_2023.day1

import java.util.stream.Stream

class Trebuchet {
    companion object {

        private val digitsInLetters = mapOf(
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

        fun readValidCalibrationsPerLines(
            puzzleInput: Stream<String>,
        ): List<Int> {
            val combinationForTheLine: MutableList<Int> = mutableListOf()
            puzzleInput.toList().joinToString("\n").lines().forEach { line ->
                val digitsOnly: MutableList<Pair<Int, Int>> = getAllDigitsWithTheirPosition(line)
                val mixedDigitsAndTheirPositions = getAllDigitsInLettersWithTheirIndexes(line)
                digitsOnly.addAll(mixedDigitsAndTheirPositions)

                val orderedDigitsWithLetters: List<Pair<Int, Int>> = digitsOnly.sortedBy { it.first }
                val firstDigit = orderedDigitsWithLetters.first().second
                val lastDigit = orderedDigitsWithLetters.last().second
                combinationForTheLine.add("$firstDigit$lastDigit".toInt())
            }
            return combinationForTheLine.toList()
        }

        private fun getAllDigitsInLettersWithTheirIndexes(
            line: String,
        ): MutableList<Pair<Int, Int>> {
            val mixedDigitsAndTheirPositions: MutableList<Pair<Int, Int>> = mutableListOf()
            digitsInLetters.keys.forEach { digitAsLetter ->
                val occurences: List<Int> = line.indexesOf(digitAsLetter)
                if (occurences.isNotEmpty()) {
                    occurences.forEach {
                        val valueAsDigit: Int = digitsInLetters[digitAsLetter]!!
                        mixedDigitsAndTheirPositions.add(Pair(it, valueAsDigit))
                    }
                }
            }
            return mixedDigitsAndTheirPositions
        }

        private fun getAllDigitsWithTheirPosition(line: String): MutableList<Pair<Int, Int>> {
            val allDigits: MutableList<Pair<Int, Int>> = mutableListOf()
            line.toCharArray().forEachIndexed { index, input ->
                if (input.isDigit()) {
                    val aDigit = Pair(index, input.digitToInt())
                    allDigits.add(aDigit)
                }
            }
            return allDigits
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
    }

}

fun String.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
    return this.let {
        val regex = if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr)
        regex.findAll(this).map { it.range.first }.toList()
    }
}
