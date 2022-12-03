package advent_of_code.year_2022.day2

import advent_of_code.year_2022.day2.RockPaperScissors.*
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

        @Test
        fun `I play paper against rock and I should win with a score of 8`(){
            val round1 = Pair('A', 'Y')
            val pair = rockPaperScissors.computeMyScoreForRound(round1, rules)
            val winningShape = pair.first
            val myScore = pair.second

            Assertions.assertThat(winningShape).isEqualTo(Shape.PAPER)
            Assertions.assertThat(myScore).isEqualTo(8)
        }

        @Test
        fun `I play Rock against Paper and I loose  with a score of 1`(){
            val round = Pair('B', 'X')
            val pair = rockPaperScissors.computeMyScoreForRound(round, rules)
            val winningShape = pair.first
            val myScore = pair.second

            Assertions.assertThat(winningShape).isEqualTo(Shape.PAPER)
            Assertions.assertThat(myScore).isEqualTo(1)
        }

        @Test
        fun `we both Scissors thus nobody wins and I have a score of 6`(){
            val round = Pair('C', 'Z')
            val pair = rockPaperScissors.computeMyScoreForRound(round, rules)
            val winningShape = pair.first
            val myScore = pair.second
            Assertions.assertThat(winningShape).isEqualTo(Shape.SCISSORS)
            Assertions.assertThat(myScore).isEqualTo(6)
        }

        @Test
        fun `should compute my total score if everything goes according to my strategy guide`(){
            val allRounds = listOf(
                Pair('A', 'Y'),
                Pair('B', 'X'),
                Pair('C', 'Z'),
            )
            val myScore = rockPaperScissors.computeMyScoreForAllRounds(allRounds, rules)

            Assertions.assertThat(myScore.sum()).isEqualTo(15)
        }

        @Test
        fun `should compute my total score if everything goes according to my strategy guide -- file version`(){
             val filePath = "src/test/resources/2022/day2"
             val allRounds = BufferedReader(FileReader("$filePath/sample.txt")).lines().map {
                val shapesPlayed  = it.split(" ")
                Pair(shapesPlayed[0].single(), shapesPlayed[1].single())
            }.toList()

            val myScore = rockPaperScissors.computeMyScoreForAllRounds(allRounds, rules)

            Assertions.assertThat(myScore.sum()).isEqualTo(15)
        }

        @Test
        fun `Part_2 counting my scores given how the opponent plays and how the game ends`(){
            val round = Pair('A', 'Y')

        }

    }

    @Nested
    inner class Puzzle {
        private val filePath = "src/test/resources/2022/day2"
        private val allRounds = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines().map {
            val shapesPlayed  = it.split(" ")
            Pair(shapesPlayed[0].single(), shapesPlayed[1].single())
        }.toList()

        @Test
        fun `_part1_ Should return the my total score if everything goes according to my strategy guide`(){
            val myScore = rockPaperScissors.computeMyScoreForAllRounds(allRounds, rules)
            Assertions.assertThat(myScore.sum()).isEqualTo(9651)
        }


    }

}
