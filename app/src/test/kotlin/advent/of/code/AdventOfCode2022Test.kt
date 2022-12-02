package advent.of.code

import com.google.common.collect.ImmutableMap
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
        fun `_puzzle_ - Should read the raw metrics and return max calories carried by an elf`(){
            val filePath = "src/test/resources/2022/day1"
            val caloriesMeasurements = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines().toList()
            val allMeasurements = computeCaloriesCarriedByEachElf(caloriesMeasurements)
            val maxCalories = allMeasurements.entries.maxOf { it.value }
            Assertions.assertThat(maxCalories).isEqualTo(72017)
        }
    }

    private fun computeCaloriesCarriedByEachElf(caloriesMeasurements: List<String>): Map<Int, Int> {
        val allMeasurements = mutableMapOf<Int, Int>()
        var currentElfIndex = caloriesMeasurements.indexOfFirst { it.isEmpty() }.plus(1)
        var accumulator = 0
        caloriesMeasurements.forEach { rawMeasurement ->
            if (!rawMeasurement.isEmpty()) {
                accumulator += rawMeasurement.toInt()
            } else {
                allMeasurements[currentElfIndex] = accumulator
                accumulator = 0
                currentElfIndex++
            }
        }
        return allMeasurements
    }


}
