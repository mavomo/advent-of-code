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
        val opponentShape = currentGame.first
        val myShape = currentGame.second
        val pair = computeMyScoreForSingleGame(opponentShape, myShape, rules, currentGame)

        return Pair(pair.second, pair.first)
    }

    private fun computeMyScoreForSingleGame(
        opponentShape: Shape,
        myShape: Shape,
        rules: Map<Pair<Shape, Shape>, Shape>,
        currentGame: Pair<Shape, Shape>
    ): Pair<Int, Shape> {
        val winningShape: Shape
        var myScore = 0
        if (opponentShape != myShape) {
            winningShape = rules[currentGame]!!
            if (winningShape == myShape) {
                val isSameShape = myShape === opponentShape
                myScore = (if (isSameShape) computeRoundScore(myShape, DRAW) else computeRoundScore(myShape, VICTORY))
            } else {
                myScore += computeRoundScore(myShape, DEFEAT)
            }
        } else {
            winningShape = myShape
            myScore = computeRoundScore(myShape, DRAW)
        }
        return Pair(myScore, winningShape)
    }

    private fun computeRoundScore(myShape: Shape, expectedODD: ODD): Int {
        var myScore = myScoreByRoundResult[expectedODD]!!
        myScore += scoresByShape[myShape]!!
        return myScore
    }


    fun computeMyScoreForAllRounds(
        allRounds: List<Pair<Char, Char>>, rules: Map<Pair<Shape, Shape>, Shape>
    ): ImmutableList<Int> {
        val scores = mutableListOf<Int>()
        allRounds.forEach {
            val currentGame = Pair(shapesByOpponent[it.first]!!, myShapesCombination[it.second]!!)
            val myScoreThisRound = computeMyScoreForRoundGuessingTheResult(rules, currentGame)
            scores.add(myScoreThisRound.second)
        }
        return ImmutableList.copyOf(scores)
    }

    fun computeMyScoreKnowingTheResult(
        currentInput: Pair<Shape, ODD>,
        rulesBook: Map<Pair<Shape, Shape>, Shape>
    ): Int {
        val myShape: Shape = findMyMatchingShape(currentInput, rulesBook)
        val myScore = myScoreByRoundResult[currentInput.second]!!.plus(scoresByShape[myShape]!!)

        return myScore
    }

    private fun findMyMatchingShape(
        currentInput: Pair<Shape, ODD>,
        rulesBook: Map<Pair<Shape, Shape>, Shape>,
    ) = when (currentInput.second) {
        DRAW -> rulesBook.values.find { it == currentInput.first }!!
        DEFEAT -> {
            val suggestedGame = rulesBook.entries.find { it.key.first == currentInput.first && it.value == currentInput.first }
            suggestedGame!!.key.second
        }
        VICTORY -> {
            val suggestedGame = rulesBook.entries.find { it.key.first == currentInput.first && it.value != currentInput.first }
            suggestedGame!!.key.second
        }
    }

    fun computeMyGameScoreKnowingTheResult(
        allRounds: List<Pair<Char, Char>>,
        rules: Map<Pair<Shape, Shape>, Shape>
    ): Int {
        val scores = mutableListOf<Int>()
        allRounds.forEach {
            val opponentShape = shapesByOpponent[it.first]!!
            val expectedResult = Companion.expectedResult[it.second]!!
            val currentGame: Pair<Shape, ODD> = Pair(opponentShape, expectedResult)
            val myScoreThisRound = computeMyScoreKnowingTheResult(currentGame, rules)
            scores.add(myScoreThisRound)
        }
        return scores.sum()
    }
}
