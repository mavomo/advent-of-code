package advent_of_code.year_2022.day2

import com.google.common.collect.ImmutableList


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

    private val endedRound: Map<Char, ODD> = mapOf(
        'X' to ODD.DEFEAT,
        'Y' to ODD.DRAW,
        'Z' to ODD.VICTORY
    )



     fun computeMyScoreForRound(
         round: Pair<Char, Char>, rules: Map<Pair<Shape, Shape>, Shape>
     ): Pair<Shape?, Int> {
        val opponentShape: Shape = shapesByOpponent[round.first]!!
        val myShape: Shape = myShapesCombination[round.second]!!
        val currentGame = Pair(opponentShape, myShape)
        val winningShape : Shape;
         var myScore: Int
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

    fun computeMyScoreForAllRounds(
        allRounds: List<Pair<Char, Char>>, rules: Map<Pair<Shape, Shape>, Shape>
    ): ImmutableList<Int> {
        val scores = mutableListOf<Int>()
        allRounds.forEach {
            val myScoreThisRound = computeMyScoreForRound(it, rules)
            scores.add(myScoreThisRound.second)
        }
        return ImmutableList.copyOf(scores)
    }

}
