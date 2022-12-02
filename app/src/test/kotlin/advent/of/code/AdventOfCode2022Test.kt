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
        private fun String.isLastElement(caloriesMeasurements: List<String>): Boolean {
            val indexOfCurrentMeasure = caloriesMeasurements.indexOf(this)
            val lastElement = caloriesMeasurements.indexOfLast { it.isNotEmpty() }
            return indexOfCurrentMeasure==lastElement
        }
    }

    @Nested
    inner class Day2 {
        private val scoresByShape = mapOf(
            Shape.ROCK to 1,
            Shape.PAPER to 2,
            Shape.SCISSORS to 3
        )
        private val myScoreByRoundResult = mapOf(
            ODD.DRAW to 3,
            ODD.VICTORY to 6,
            ODD.DEFEAT to 0
        )
        private val shapesByOpponent: Map<Char, Shape> =  mapOf(
            'A' to Shape.ROCK,
            'B' to Shape.PAPER,
            'C' to Shape.SCISSORS
        )
        private val myShapesCombination: Map<Char, Shape> = mapOf(
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
        fun `_sample_ I play paper against rock and I should win with a score of 8`(){
            val round1 = Pair('A', 'Y')
            val pair = computeMyScoreForRound(round1)
            val winningShape = pair.first
            val myScore = pair.second

            Assertions.assertThat(winningShape).isEqualTo(Shape.PAPER)
            Assertions.assertThat(myScore).isEqualTo(8)
        }

        @Test
        fun `_sample_ I play Rock against Paper and I loose  with a score of 1`(){
            val round = Pair('B', 'X')
            val pair = computeMyScoreForRound(round)
            val winningShape = pair.first
            val myScore = pair.second

            Assertions.assertThat(winningShape).isEqualTo(Shape.PAPER)
            Assertions.assertThat(myScore).isEqualTo(1)
        }

        @Test
        fun `_sample_ we both Scissors thus nobody wins and I have a score of 6`(){
            val round = Pair('C', 'Z')
            val pair = computeMyScoreForRound(round)
            val winningShape = pair.first
            val myScore = pair.second
            Assertions.assertThat(winningShape).isEqualTo(Shape.SCISSORS)
            Assertions.assertThat(myScore).isEqualTo(6)
        }

        @Test
        fun `_sample_ should compute my total score if everything goes according to my strategy guide`(){
            val allRounds = listOf(
                Pair('A', 'Y'),
                Pair('B', 'X'),
                Pair('C', 'Z'),
            )
            val scores = mutableListOf<Int>()
            allRounds.forEach {
              val myScoreThisRound = computeMyScoreForRound(it)
                scores.add(myScoreThisRound.second)
            }
            Assertions.assertThat(scores.sum()).isEqualTo(15)
        }

        private fun computeMyScoreForRound(round1: Pair<Char, Char>): Pair<Shape?, Int> {
            val opponentShape: Shape = shapesByOpponent[round1.first]!!
            val myShape: Shape = myShapesCombination[round1.second]!!
            val currentGame = Pair(opponentShape, myShape)
            val winningShape : Shape;
            var myScore = 0
            if(opponentShape != myShape){
                winningShape = rules[currentGame]!!
                if (winningShape == myShape) {
                    val isSameShape = myShape === opponentShape
                    myScore = (if (isSameShape) myScoreByRoundResult[ODD.DRAW]!! else myScoreByRoundResult[ODD.VICTORY]!!)
                    myScore += scoresByShape[winningShape]!!
                }else {
                    myScore = myScoreByRoundResult[ODD.DEFEAT]!!
                    myScore += scoresByShape[myShape]!!
                }
            }else {
                winningShape = myShape
                myScore = myScoreByRoundResult[ODD.DRAW]!!
                myScore += scoresByShape[myShape]!!
            }
            return Pair(winningShape, myScore)
        }

    }

    enum class Shape {
        ROCK, PAPER, SCISSORS
    }

    enum class ODD {
        VICTORY, DEFEAT, DRAW
    }

}



