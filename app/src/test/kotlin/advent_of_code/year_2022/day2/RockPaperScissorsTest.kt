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
    inner class Sample_part_1 {

        @Test
        fun `I play paper against rock and I should win with a score of 8`() {
            val round1 = Pair('A', 'Y')
            val roundWithShapes = Pair( shapesByOpponent[round1.first]!!,
                RPS.myShapesCombination[round1.second]!!)

            val pair = rockPaperScissors.computeMyScoreForRound(rules, roundWithShapes)
            val winningShape = pair.first
            val myScore = pair.second

            Assertions.assertThat(winningShape).isEqualTo(Shape.PAPER)
            Assertions.assertThat(myScore).isEqualTo(8)
        }

        @Test
        fun `I play Rock against Paper and I loose  with a score of 1`() {
            val round = Pair('B', 'X')
            val roundWithShapes = Pair(shapesByOpponent[round.first]!!,  RPS.myShapesCombination[round.second]!!)

            val pair = rockPaperScissors.computeMyScoreForRound(rules, roundWithShapes)
            val winningShape = pair.first
            val myScore = pair.second

            Assertions.assertThat(winningShape).isEqualTo(Shape.PAPER)
            Assertions.assertThat(myScore).isEqualTo(1)
        }

        @Test
        fun `we both Scissors thus nobody wins and I have a score of 6`() {
            val round = Pair('C', 'Z')
            val currentGame = Pair(shapesByOpponent[round.first]!!, RPS.myShapesCombination[round.second]!!)

            val pair = rockPaperScissors.computeMyScoreForRound(rules, currentGame)
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

        @Test
        fun `_part_2 should return a score when i am expected to have the same shape as my opponent `() {
            val roundAsADraw = Pair('A', 'Y')
            val opponentShape = shapesByOpponent[roundAsADraw.first]!!
            val expectedResult = expectedResult[roundAsADraw.second]
            var myScore = 0
            if(expectedResult == RPS.ODD.DRAW){
                 val myShape = rules.values.find { it == opponentShape }!!
                 myScore = myScoreByRoundResult[expectedResult]!!
                 myScore += scoresByShape[myShape]!!
            }
            if(expectedResult === RPS.ODD.DEFEAT){
                val myShape = rules.values.find { it == opponentShape }!!
                myScore = myScoreByRoundResult[expectedResult]!!
                myScore += scoresByShape[myShape]!!
            }
            Assertions.assertThat(myScore).isEqualTo(4)
        }

        @Test
        fun `_part_2 should return a score when i am expected to be defeated with a score of 1`() {
            val round = Pair('B', 'X')
            val opponentShape = shapesByOpponent[round.first]!!
            val expectedResult = expectedResult[round.second]
            var myScore = 0
            if(expectedResult == RPS.ODD.DRAW){
                 val myShape = rules.values.find { it == opponentShape }!!
                 myScore = myScoreByRoundResult[expectedResult]!!
                 myScore += scoresByShape[myShape]!!
            }
            if(expectedResult === RPS.ODD.DEFEAT){
                val suggestedGame = rules.entries.find { it.key.first == opponentShape && it.value == opponentShape}
                val myShape = suggestedGame!!.key.second
                myScore = myScoreByRoundResult[expectedResult]!!
                myScore += scoresByShape[myShape]!!
            }
            Assertions.assertThat(myScore).isEqualTo(1)
        }

        @Test
        fun `_part_2 should return a score when i am expected to win with a score of 7 `() {
            val round =  Pair('C', 'Z')
            val opponentShape = shapesByOpponent[round.first]!!
            val expectedResult = expectedResult[round.second]
            var myScore = 0
            if(expectedResult == RPS.ODD.DRAW){
                 val myShape = rules.values.find { it == opponentShape }!!
                 myScore = myScoreByRoundResult[expectedResult]!!
                 myScore += scoresByShape[myShape]!!
            }
            if(expectedResult === RPS.ODD.DEFEAT){
                val suggestedGame = rules.entries.find { it.key.first == opponentShape && it.value == opponentShape}
                val myShape = suggestedGame!!.key.second
                myScore = myScoreByRoundResult[expectedResult]!!
                myScore += scoresByShape[myShape]!!
            }
            if(expectedResult === RPS.ODD.VICTORY){
                val suggestedGame = rules.entries.find { it.key.first == opponentShape && it.value != opponentShape}
                val myShape = suggestedGame!!.key.second
                myScore = myScoreByRoundResult[expectedResult]!!
                myScore += scoresByShape[myShape]!!
            }
            Assertions.assertThat(myScore).isEqualTo(7)
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


    }

}
