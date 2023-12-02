package advent_of_code.year_2023.day1

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Stream

class TrebuchetTest {

    @Test
    fun `Should go with the example as spec`() {
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
    fun `_part1_ Should read the puzzle input and compute the sum of calibration values`() {
        val filePath = "src/test/resources/2023/day1"
        val calibrationValues: Stream<String> = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines()

        val combinations: MutableList<Int> = Trebuchet.getFirstAndLastCalibrations(calibrationValues)

        Assertions.assertThat(combinations.sum()).isEqualTo(55123)
    }


}
