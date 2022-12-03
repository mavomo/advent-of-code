package advent_of_code.year_2022.day2

import advent_of_code.year_2022.day2.RPS.Companion.expectedResult
import advent_of_code.year_2022.day2.RPS.Companion.myScoreByRoundResult
import advent_of_code.year_2022.day2.RPS.Companion.scoresByShape
import advent_of_code.year_2022.day2.RPS.Companion.shapesByOpponent
import advent_of_code.year_2022.day2.RPS.Shape
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileReader

internal class RockPaperScissorsTest {

    private val rockPaperScissors = RockPaperScissors()

    private val rules = mapOf(
        Pair(Shape.ROCK, Shape.SCISSORS) to Shape.ROCK,
        Pair(Shape.SCISSORS, Shape.ROCK) to Shape.ROCK,
        Pair(Shape.SCISSORS, Shape.PAPER) to Shape.SCISSORS,
        Pair(Shape.PAPER, Shape.SCISSORS) to Shape.SCISSORS,
        Pair(Shape.PAPER, Shape.ROCK) to Shape.PAPER,
        Pair(Shape.ROCK, Shape.PAPER) to Shape.PAPER,
    )

    @Nested
    inner class Sample {

        @Nested
        inner class Part1 {
            @Test
            fun `I play paper against rock and I should win with a score of 8`() {
                val round1 = Pair('A', 'Y')
                val roundWithShapes = Pair(
                    shapesByOpponent[round1.first]!!,
                    RPS.myShapesCombination[round1.second]!!
                )

                val pair = rockPaperScissors.computeMyScoreForRoundGuessingTheResult(rules, roundWithShapes)
                val winningShape = pair.first
                val myScore = pair.second

                Assertions.assertThat(winningShape).isEqualTo(Shape.PAPER)
                Assertions.assertThat(myScore).isEqualTo(8)
            }

            @Test
            fun `I play Rock against Paper and I loose  with a score of 1`() {
                val round = Pair('B', 'X')
                val roundWithShapes = Pair(shapesByOpponent[round.first]!!, RPS.myShapesCombination[round.second]!!)

                val pair = rockPaperScissors.computeMyScoreForRoundGuessingTheResult(rules, roundWithShapes)
                val winningShape = pair.first
                val myScore = pair.second

                Assertions.assertThat(winningShape).isEqualTo(Shape.PAPER)
                Assertions.assertThat(myScore).isEqualTo(1)
            }

            @Test
            fun `we both Scissors thus nobody wins and I have a score of 6`() {
                val round = Pair('C', 'Z')
                val currentGame = Pair(shapesByOpponent[round.first]!!, RPS.myShapesCombination[round.second]!!)

                val pair = rockPaperScissors.computeMyScoreForRoundGuessingTheResult(rules, currentGame)
                val winningShape = pair.first
                val myScore = pair.second
                Assertions.assertThat(winningShape).isEqualTo(Shape.SCISSORS)
                Assertions.assertThat(myScore).isEqualTo(6)
            }

            @Test
            fun `should compute my total score if everything goes according to my strategy guide`() {
                val allRounds = listOf(
                    Pair('A', 'Y'),
                    Pair('B', 'X'),
                    Pair('C', 'Z'),
                )

                val myScore = rockPaperScissors.computeMyScoreForAllRounds(allRounds, rules)

                Assertions.assertThat(myScore.sum()).isEqualTo(15)
            }
        }

        @Nested
        inner class Part2 {
            @Test
            fun `_part_2 should return a score when i am expected to have the same shape as my opponent `() {
                val roundAsADraw = Pair('A', 'Y')
                val opponentShape = shapesByOpponent[roundAsADraw.first]!!
                val expectedResult = expectedResult[roundAsADraw.second]!!
                val input : Pair<Shape, RPS.ODD> = Pair(opponentShape, expectedResult)

                val myScore = rockPaperScissors.computeMyScoreKnowingTheResult(input, rules)

                Assertions.assertThat(myScore).isEqualTo(4)
            }

            @Test
            fun `_part_2 should return a score when i am expected to be defeated with a score of 1`() {
                val round = Pair('B', 'X')
                val opponentShape = shapesByOpponent[round.first]!!
                val expectedResult = expectedResult[round.second]!!
                val input: Pair<Shape, RPS.ODD> = Pair(opponentShape, expectedResult)

                val myScore = rockPaperScissors.computeMyScoreKnowingTheResult(input, rules)

                Assertions.assertThat(myScore).isEqualTo(1)
            }

            @Test
            fun `_part_2 should return a score when i am expected to win with a score of 7 `() {
                val round = Pair('C', 'Z')
                val opponentShape = shapesByOpponent[round.first]!!
                val expectedResult = expectedResult[round.second]!!
                val input: Pair<Shape, RPS.ODD> = Pair(opponentShape, expectedResult)

                val myScore = rockPaperScissors.computeMyScoreKnowingTheResult(input, rules)

                Assertions.assertThat(myScore).isEqualTo(7)
            }

            @Test
            fun `_part_2_ should return the total score for the whole game`(){
                val allRounds = listOf(
                    Pair('A', 'Y'),
                    Pair('B', 'X'),
                    Pair('C', 'Z'),
                )

                val myScore = rockPaperScissors.computeMyGameScoreKnowingTheResult(allRounds, rules)

                Assertions.assertThat(myScore).isEqualTo(12)

            }

        }


    }

    @Nested
    inner class Puzzle {
        private val filePath = "src/test/resources/2022/day2"
        private val allRounds = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines().map {
            val shapesPlayed = it.split(" ")
            Pair(shapesPlayed[0].single(), shapesPlayed[1].single())
        }.toList()

        @Test
        fun `_part1_ Should return the my total score if everything goes according to my strategy guide`() {
            val myScore = rockPaperScissors.computeMyScoreForAllRounds(allRounds, rules)
            Assertions.assertThat(myScore.sum()).isEqualTo(9651)
        }

        @Test
        fun `part_2 should compute my score given the expected output of the round`(){

            val myScore = rockPaperScissors.computeMyGameScoreKnowingTheResult(allRounds, rules)

            Assertions.assertThat(myScore).isEqualTo(10560)

        }


    }

}
