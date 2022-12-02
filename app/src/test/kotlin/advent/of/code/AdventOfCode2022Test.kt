package advent.of.code

import org.assertj.core.api.Assertions
import org.assertj.core.data.MapEntry
import org.checkerframework.checker.units.qual.A
import org.checkerframework.checker.units.qual.C
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
        private fun String.isLastElement(caloriesMeasurements: List<String>): Boolean {
            val indexOfCurrentMeasure = caloriesMeasurements.indexOf(this)
            val lastElement = caloriesMeasurements.indexOfLast { it.isNotEmpty() }
            return indexOfCurrentMeasure==lastElement
        }
    }

    @Nested
    inner class Day2 {
        val scoresByShape = mapOf(
            Shape.ROCK to 1,
            Shape.PAPER to 2,
            Shape.SCISSORS to 3
        )
        val myScoreByRoundResult = mapOf(
            ODD.DRAW to 3,
            ODD.VICTORY to 6,
            ODD.DEFEAT to 0
        )
        val shapesByOpponent: Map<Char, Shape> =  mapOf(
            'A' to Shape.ROCK,
            'B' to Shape.PAPER,
            'C' to Shape.SCISSORS
        )
        val myShapesCombination: Map<Char, Shape> = mapOf(
            'X' to Shape.ROCK,
            'Y' to Shape.PAPER,
            'Z' to Shape.SCISSORS
        )

        private val rules = mapOf(
            Pair(Shape.ROCK, Shape.SCISSORS) to Shape.ROCK,
            Pair(Shape.SCISSORS, Shape.ROCK) to Shape.ROCK,
            Pair(Shape.PAPER, Shape.SCISSORS) to Shape.SCISSORS,
            Pair(Shape.PAPER, Shape.SCISSORS) to Shape.SCISSORS,
            Pair(Shape.ROCK, Shape.PAPER) to Shape.PAPER,
            Pair(Shape.PAPER, Shape.ROCK) to Shape.PAPER,
        )

        @Test
        fun `_sample_ paper beats rock given with score of 8`(){
            val round1 = Pair('A', 'Y')
            val opponentShape: Shape = shapesByOpponent[round1.first]!!
            val myShape: Shape = myShapesCombination[round1.second]!!
            val currentGame = Pair(opponentShape, myShape)
            val winningShape = rules[currentGame]
            var score= 0
            if (winningShape == myShape){
                val isSameShape = myShape === opponentShape
                if(isSameShape) {
                    score = myScoreByRoundResult[ODD.DRAW]!!
                }else {
                    score = myScoreByRoundResult[ODD.VICTORY]!!
                }
                score += scoresByShape[winningShape]!!
            }
            Assertions.assertThat(winningShape).isEqualTo(Shape.PAPER)
            Assertions.assertThat(score).isEqualTo(8)
        }
    }

    enum class Shape {
        ROCK, PAPER, SCISSORS
    }

    enum class ODD {
        VICTORY, DEFEAT, DRAW
    }

}



