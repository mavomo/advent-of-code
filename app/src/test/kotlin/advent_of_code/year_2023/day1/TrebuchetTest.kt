package advent_of_code.year_2023.day1

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Stream

class TrebuchetTest {

    @Nested
    inner class Examples {
        @Test
        fun `_Part1_ Should get the combination of the first and last calibration and their sum`() {
            val calibrationValues = """
          1abc2
          pqr3stu8vwx
          a1b2c3d4e5f
          treb7uchet
        """.trimIndent()

            val combination: MutableList<Int> = Trebuchet.getFirstAndLastCalibrations(Stream.of(calibrationValues))

            Assertions.assertThat(combination).containsExactly(12, 38, 15, 77)
            Assertions.assertThat(combination.sum()).isEqualTo(142)
        }

        @Test
        fun `_Part2_ Should return the first and last digit in full letter given a sequence`() {
            val calibationsValues = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
        """.trimIndent()

            val allCalibrationValues = Trebuchet.getFirstAndLastAppearingDigitsInLetters(calibationsValues)

            Assertions.assertThat(allCalibrationValues)
                .containsExactly(
                    "two,nine",
                    "eight,three",
                    "one,three",
                    "two,four",
                    "nine,seven",
                    "one,eight",
                    "six,six"
                )
        }

        @Test
        fun `Part_2 Should return the first and last calibration given a sequence with digits in spelled out with letters`() {
            val calibationsValuesInLetters = """
            two,nine
            eight,three
            one,three
            two,four
            nine,seven
            one,eight
            six,six
        """.trimIndent()
            val calibrationsInFullDigits = Trebuchet.getValidCalibrationsFromLettersToDigits(calibationsValuesInLetters)

            Assertions.assertThat(calibrationsInFullDigits)
                .containsExactly(
                    "2,9", "8,3", "1,3", "2,4", "9,7", "1,8", "6,6"
                )
        }

        @Test
        fun `Should treat a whole line with the digits and the spelled in letters ones`() {
            val sampleInput = """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
            """.trimIndent()

            val combinationForTheLine: List<Int> = Trebuchet.readValidCalibrationsPerLines(Stream.of(sampleInput))

            Assertions.assertThat(combinationForTheLine).containsExactly(
                29,
                83,
                13,
                24,
                42,
                14,
                76
            )

            Assertions.assertThat(combinationForTheLine.sum())
                .isEqualTo(281)
        }

        @Test
        fun `Should do something`(){
            val sample= """
               eightthree6qskmkzs
               cmkvmr3srbsnq7onefourbfsrbjvr
               t3mtgjq4stm84
               fiveseven2grlmfhmfg8bsb
               9bgbplvtzstdsevenonedrbxhftrxgmqftjmdrr
               sixfivezptk6
               8mftfiveninedgfmtwo9three9
               pscstrfnrpllhone5fivefourtwo
               five33
               4threegfs
               6sixthreebvq5
               tsjvdsljzfgfive6threemqjfhrsqkgfznbt
               six8five
               twoznvvqgmd5jsxltq
               zvh9one
               4threesevenfivesix2hmm
               l8ccxxhqqjb1qltqxht9qknltdbmdbmone
               psevenfournine7bzphqxtfmfhsbtxxldhcqj
               9bzxmpnlqmt8
               two73nineeighteight
               5sixseven
            """.trimIndent()


            val combinationForTheLine: List<Int> = Trebuchet.readValidCalibrationsPerLines(Stream.of(sample))

            Assertions.assertThat(combinationForTheLine).containsExactly(
                86,
                34,
                34,
                58,
                91,
                66,
                89,
                12,
                53,
                43,
                65,// new start
                53,
                65,
                25,
                91,
                42,
                81,
                77,
                98,
                28,
                57
            )

        }


    }

    @Nested
    inner class Puzzles {
        @Test
        fun `_Part1_ Should read the puzzle input and compute the sum of calibration values`() {
            val filePath = "src/test/resources/2023/day1"
            val calibrationValues: Stream<String> = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines()

            val combinations: MutableList<Int> = Trebuchet.getFirstAndLastCalibrations(calibrationValues)

            Assertions.assertThat(combinations.sum()).isEqualTo(55123)
        }

        @Test
        fun `_Part2_ Should read the puzzle input with the digits and the spelled in letters ones and compute the sum`() {
            val filePath = "src/test/resources/2023/day1"
            val calibrationValues: Stream<String> = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines()

            val combinationForTheLine: List<Int> = Trebuchet.readValidCalibrationsPerLines(calibrationValues)


            Assertions.assertThat(combinationForTheLine.sum()).isEqualTo(55297)

        }
    }


}
