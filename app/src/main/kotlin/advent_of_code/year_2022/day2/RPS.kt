package advent_of_code.year_2022.day2

class RPS {
    enum class Shape {
        ROCK, PAPER, SCISSORS
    }

    enum class ODD {
        VICTORY, DEFEAT, DRAW
    }
    companion object {
         val scoresByShape = mapOf(
            Shape.ROCK to 1,
            Shape.PAPER to 2,
            Shape.SCISSORS to 3
        )
        val myScoreByRoundResult = mapOf(
            ODD.DRAW to 3,
            ODD.VICTORY to 6,
            ODD.DEFEAT to 0
        )
        val shapesByOpponent: Map<Char, Shape> =  mapOf(
            'A' to Shape.ROCK,
            'B' to Shape.PAPER,
            'C' to Shape.SCISSORS
        )
         val myShapesCombination: Map<Char, Shape> = mapOf(
            'X' to Shape.ROCK,
            'Y' to Shape.PAPER,
            'Z' to Shape.SCISSORS
        )

        val expectedResult: Map<Char, ODD> = mapOf(
            'X' to ODD.DEFEAT,
            'Y' to ODD.DRAW,
            'Z' to ODD.VICTORY
        )

    }
}
