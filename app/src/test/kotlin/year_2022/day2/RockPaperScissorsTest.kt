package year_2022.day2

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class RockPaperScissorsTest {

    private val rockPaperScissors = RockPaperScissors()

    @Nested
    inner class Sample {

        @Test
        fun `_sample_ I play paper against rock and I should win with a score of 8`(){
            val round1 = Pair('A', 'Y')
            val pair = rockPaperScissors.computeMyScoreForRound(round1)
            val winningShape = pair.first
            val myScore = pair.second

            Assertions.assertThat(winningShape).isEqualTo(RockPaperScissors.Shape.PAPER)
            Assertions.assertThat(myScore).isEqualTo(8)
        }

        @Test
        fun `_sample_ I play Rock against Paper and I loose  with a score of 1`(){
            val round = Pair('B', 'X')
            val pair = rockPaperScissors.computeMyScoreForRound(round)
            val winningShape = pair.first
            val myScore = pair.second

            Assertions.assertThat(winningShape).isEqualTo(RockPaperScissors.Shape.PAPER)
            Assertions.assertThat(myScore).isEqualTo(1)
        }

        @Test
        fun `_sample_ we both Scissors thus nobody wins and I have a score of 6`(){
            val round = Pair('C', 'Z')
            val pair = rockPaperScissors.computeMyScoreForRound(round)
            val winningShape = pair.first
            val myScore = pair.second
            Assertions.assertThat(winningShape).isEqualTo(RockPaperScissors.Shape.SCISSORS)
            Assertions.assertThat(myScore).isEqualTo(6)
        }

        @Test
        fun `_sample_ should compute my total score if everything goes according to my strategy guide`(){
            val allRounds = listOf(
                Pair('A', 'Y'),
                Pair('B', 'X'),
                Pair('C', 'Z'),
            )
            val myScore = rockPaperScissors.computeMyScoreForAllRounds(allRounds)

            Assertions.assertThat(myScore.sum()).isEqualTo(15)
        }
    }

}
