package advent.of.code

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileReader

class AdventOfCode2022Test {

    @Nested
    inner class DayOne {
        @Test
        fun `_sample_ - Should read the raw metrics and return max calories carried by an elf`(){
            val filePath = "src/test/resources/2022/day1"
            val caloriesMeasurements = BufferedReader(FileReader("$filePath/sample.txt")).lines().toList()
            val allMeasurements = computeCaloriesCarriedByEachElf(caloriesMeasurements)
            val maxCalories = allMeasurements.entries.maxOf { it.value }
            Assertions.assertThat(maxCalories).isEqualTo(24000)
        }

        @Test
        fun `_puzzle_part_1_ - Should read the raw metrics and return max calories carried by an elf`(){
            val filePath = "src/test/resources/2022/day1"
            val caloriesMeasurements = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines().toList()
            val allMeasurements = computeCaloriesCarriedByEachElf(caloriesMeasurements)
            val maxCalories = allMeasurements.entries.maxOf { it.value }
            Assertions.assertThat(maxCalories).isEqualTo(72017)
        }

        @Test
        fun `_sample_part_2 total calories carried by the top three elves carrying the most calories `(){
            val filePath = "src/test/resources/2022/day1"
            val caloriesMeasurements = BufferedReader(FileReader("$filePath/sample.txt")).lines().toList()
            val allMeasurements: Map<Int, Int> = computeCaloriesCarriedByEachElf(caloriesMeasurements)
            val totalCaloriesForTheTopThree = allMeasurements.entries.sortedByDescending { it.value }
            val maxCalories = totalCaloriesForTheTopThree.take(3).sumOf { it.value }
            Assertions.assertThat(maxCalories).isEqualTo(45000)
        }

        @Test
        fun `_puzzle_part_2 total calories carried by the top three elves carrying the most calories `(){
            val filePath = "src/test/resources/2022/day1"
            val caloriesMeasurements = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines().toList()
            val allMeasurements: Map<Int, Int> = computeCaloriesCarriedByEachElf(caloriesMeasurements)
            val totalCaloriesForTheTopThree = allMeasurements.entries.sortedByDescending { it.value }
            val totalCarriedByTopThreeElves = totalCaloriesForTheTopThree.take(3).sumOf { it.value }
            Assertions.assertThat(totalCarriedByTopThreeElves).isEqualTo(212520)
        }



        private fun computeCaloriesCarriedByEachElf(caloriesMeasurements: List<String>): Map<Int, Int> {
            val allMeasurements = mutableMapOf<Int, Int>()
            var currentElfIndex = caloriesMeasurements.indexOfFirst { it.isEmpty() }.plus(1)
            var accumulator = 0
            caloriesMeasurements.forEach { rawMeasurement ->
                if (rawMeasurement.isNotEmpty() && !rawMeasurement.isLastElement(caloriesMeasurements) ) {
                    accumulator += rawMeasurement.toInt()
                }else{
                    val sumOfCalories = if(rawMeasurement.isLastElement(caloriesMeasurements)) rawMeasurement.toInt() else accumulator
                    allMeasurements[currentElfIndex] = sumOfCalories
                    accumulator = 0
                    currentElfIndex++
                }
            }
            return allMeasurements
        }
    }
}

private fun String.isLastElement(caloriesMeasurements: List<String>): Boolean {
    val indexOfCurrentMeasure = caloriesMeasurements.indexOf(this)
    val lastElement = caloriesMeasurements.indexOfLast { it.isNotEmpty() }
    return indexOfCurrentMeasure==lastElement
}
