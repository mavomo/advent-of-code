package advent_of_code.year_2022.day6

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class TuningTroubleTest {

    @Test
    fun `Should do something`() {
        // val signal = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
        //val signal2 = "bvwbjplbgvbhsrlpgdmjqwftvncz"
        val signal3 = "nppdvjthqldpwncqszvftbrmjlhg"
        //val marker = findTheMarker(signal)
        // val marker2 = findTheMarker(signal2)
        val marker3 = findTheMarker(signal3)

        Assertions.assertThat(marker3).isEqualTo(6)
        //Assertions.assertThat(marker).isEqualTo(7)
        //Assertions.assertThat(marker2).isEqualTo(5)
        // Assertions.assertThat(marker).isEqualTo(7)
    }

    private fun findTheMarker(
        signal: String
    ): Int {

        val dataStream = signal.chunked(3)
        val head: String = dataStream.first()
        var marker = head.lastIndex
        val distinctChar = head.toCharArray().distinct()
        val finalDataStream = mutableSetOf<Char>()
        finalDataStream.addAll(distinctChar)
        if (distinctChar.size < 3) {
            for (Idx in 1 until dataStream.size) {
                val nextDataStream = dataStream[Idx]
                val yyyyy = nextDataStream.toCharArray()
                for (y in yyyyy) {
                    val differentChars = finalDataStream.distinct()
                    if (differentChars.size != 4 && marker == head.lastIndex) {
                        finalDataStream.add(y)
                    } else if (differentChars.size == 4 && marker == head.lastIndex) {
                        marker = signal.indexOf(y) + 1
                    }
                }
            }
        }
        return marker
    }
}
