package advent_of_code.year_2022.day2

import advent_of_code.year_2022.day2.RPS.*
import advent_of_code.year_2022.day2.RPS.Companion.myScoreByRoundResult
import advent_of_code.year_2022.day2.RPS.Companion.myShapesCombination
import advent_of_code.year_2022.day2.RPS.Companion.scoresByShape
import advent_of_code.year_2022.day2.RPS.Companion.shapesByOpponent
import advent_of_code.year_2022.day2.RPS.ODD.*
import com.google.common.collect.ImmutableList

class RockPaperScissors {

    fun computeMyScoreForRoundGuessingTheResult(
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
                myScore = (if (isSameShape) myScoreByRoundResult[DRAW]!! else myScoreByRoundResult[VICTORY]!!)
                myScore += scoresByShape[winningShape]!!
            }else {
                myScore = myScoreByRoundResult[DEFEAT]!!
                myScore += scoresByShape[myShape]!!
            }
        }else {
            winningShape = myShape
            myScore = myScoreByRoundResult[DRAW]!!
            myScore += scoresByShape[myShape]!!
        }
        return Pair(winningShape, myScore)
    }



    fun computeMyScoreForAllRounds(
        allRounds: List<Pair<Char, Char>>, rules: Map<Pair<Shape, Shape>, Shape>
    ): ImmutableList<Int> {
        val scores = mutableListOf<Int>()
        allRounds.forEach {
            val currentGame = Pair(shapesByOpponent[it.first]!!,  myShapesCombination[it.second]!!)
            val myScoreThisRound = computeMyScoreForRoundGuessingTheResult(rules, currentGame)
            scores.add(myScoreThisRound.second)
        }
        return ImmutableList.copyOf(scores)
    }

     fun computeMyScoreKnowingTheResult(
        currentInput: Pair<Shape, ODD>, rulesBook: Map<Pair<Shape, Shape>, Shape>
    ): Int {
        var myScore = 0
        val opponentShape = currentInput.first
        val expectedResult = currentInput.second
        if (expectedResult == DRAW) {
            val myShape = rulesBook.values.find { it == opponentShape }!!
            myScore = myScoreByRoundResult[expectedResult]!!
            myScore += scoresByShape[myShape]!!
        }
        if (expectedResult === DEFEAT) {
            val suggestedGame =
                rulesBook.entries.find { it.key.first == opponentShape && it.value == opponentShape }
            val myShape = suggestedGame!!.key.second
            myScore = myScoreByRoundResult[expectedResult]!!
            myScore += scoresByShape[myShape]!!
        }
        if (expectedResult === VICTORY) {
            val suggestedGame =
                rulesBook.entries.find { it.key.first == opponentShape && it.value != opponentShape }
            val myShape = suggestedGame!!.key.second
            myScore = myScoreByRoundResult[expectedResult]!!
            myScore += scoresByShape[myShape]!!
        }
        return myScore
    }
}
