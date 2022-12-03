package advent_of_code.year_2022.day2

import advent_of_code.year_2022.day2.RPS.*
import advent_of_code.year_2022.day2.RPS.Companion.myScoreByRoundResult
import advent_of_code.year_2022.day2.RPS.Companion.myShapesCombination
import advent_of_code.year_2022.day2.RPS.Companion.scoresByShape
import advent_of_code.year_2022.day2.RPS.Companion.shapesByOpponent
import com.google.common.collect.ImmutableList

class RockPaperScissors {
     fun computeMyScoreForRound(
         rules: Map<Pair<Shape, Shape>, Shape>,
         currentGame: Pair<Shape, Shape>
     ): Pair<Shape?, Int> {
         val opponentShape= currentGame.first
         val myShape = currentGame.second
        val winningShape : Shape
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

    private fun scoreByRoundResult(winningShape: Shape, myShape: Shape): Int {
        val isSameShape =  winningShape == myShape
         if (isSameShape)
             return myScoreByRoundResult[ODD.DRAW]!!
         else
             return myScoreByRoundResult[ODD.VICTORY]!!
    }

    fun computeMyScoreForAllRounds(
        allRounds: List<Pair<Char, Char>>, rules: Map<Pair<Shape, Shape>, Shape>
    ): ImmutableList<Int> {
        val scores = mutableListOf<Int>()
        allRounds.forEach {
            val currentGame = Pair(shapesByOpponent[it.first]!!,  myShapesCombination[it.second]!!)
            val myScoreThisRound = computeMyScoreForRound(rules, currentGame)
            scores.add(myScoreThisRound.second)
        }
        return ImmutableList.copyOf(scores)
    }

}
