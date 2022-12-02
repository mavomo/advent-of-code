package year_2022.day2

import com.google.common.collect.ImmutableList
import javax.annotation.concurrent.Immutable


class RockPaperScissors {
    enum class Shape {
        ROCK, PAPER, SCISSORS
    }

    enum class ODD {
        VICTORY, DEFEAT, DRAW
    }
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

     fun computeMyScoreForRound(round1: Pair<Char, Char>): Pair<Shape?, Int> {
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

    fun computeMyScoreForAllRounds(allRounds: List<Pair<Char, Char>>): ImmutableList<Int> {
        val scores = mutableListOf<Int>()
        allRounds.forEach {
            val myScoreThisRound = this.computeMyScoreForRound(it)
            scores.add(myScoreThisRound.second)
        }
        return ImmutableList.copyOf(scores)
    }

}
