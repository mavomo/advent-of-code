package advent_of_code.year_2022.day1

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileReader

internal class CounterOfCaloriesTest {
    private val caloriesCounter = CounterOfCalories()

    @Nested
    inner class Sample {
       private val filePath = "src/test/resources/2022/day1"
        private val caloriesMeasurements = BufferedReader(FileReader("$filePath/sample.txt")).lines().toList()

        @Test
        fun `_part_1 - Should read the raw metrics and return max calories carried by an elf`(){
            val allMeasurements = caloriesCounter.computeCaloriesCarriedByEachElf(caloriesMeasurements)

            val maxCalories = caloriesCounter.getMaxCarriedCaloriesByASingleElf(allMeasurements)

            Assertions.assertThat(maxCalories).isEqualTo(24000)
        }

        @Test
        fun `_part_2 - total calories carried by the top three elves carrying the most calories `(){
            val allMeasurements: Map<Int, Int> = caloriesCounter.computeCaloriesCarriedByEachElf(caloriesMeasurements)
            val totalCaloriesForTheTopThree = caloriesCounter.getTotalCaloriesForTheHeaviestTop3(allMeasurements)
            Assertions.assertThat(totalCaloriesForTheTopThree).isEqualTo(45000)
        }
    }

    @Nested
    inner class Puzzle {
        private val filePath = "src/test/resources/2022/day1"
        private val caloriesMeasurements = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines().toList()

        @Test
        fun `_part_1_ - Should read the raw metrics and return max calories carried by an elf`(){
                val allMeasurements = caloriesCounter.computeCaloriesCarriedByEachElf(caloriesMeasurements)
            val maxCalories = caloriesCounter.getMaxCarriedCaloriesByASingleElf(allMeasurements)
            Assertions.assertThat(maxCalories).isEqualTo(72017)
        }

        @Test
        fun `_puzzle_part_2 total calories carried by the top three elves carrying the most calories `(){

            val allMeasurements: Map<Int, Int> = caloriesCounter.computeCaloriesCarriedByEachElf(caloriesMeasurements)
            val totalCarriedByTopThreeElves = caloriesCounter.getTotalCaloriesForTheHeaviestTop3(allMeasurements)
            Assertions.assertThat(totalCarriedByTopThreeElves).isEqualTo(212520)
        }

    }
}
