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
        fun `Should treat a whole line with the digits and the spelled in letters ones`() {
            val sampleInput = """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
                sixfconesix
            """.trimIndent()

            val combinationForTheLine: List<Int> = Trebuchet.readValidCalibrationsPerLines(Stream.of(sampleInput))

            Assertions.assertThat(combinationForTheLine).containsExactly(
                29,
                83,
                13,
                24,
                42,
                14,
                76,
                66
            )
            Assertions.assertThat(combinationForTheLine.sum())
                .isEqualTo(347)
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

            Assertions.assertThat(combinationForTheLine.sum()).isEqualTo(55260)

        }
    }


}


