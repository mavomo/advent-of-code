package advent_of_code.year_2022.day4

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileReader

internal class CampCleanupTest {
    private val campCleanup = CampCleanup()

    @Nested
    inner class Sample {
        @Test
        fun `Should count the number of groups where their assignements if fully contained by the other group`() {
            val assignmentFirstPair = "2-4,6-8"
            val assignmentAnotherPair = "2-8,3-7"
            val commonSectorsForFirstPair = campCleanup.getCommonAssignedSections(assignmentFirstPair)
            val commonSectorsForSecondPair = campCleanup.getCommonAssignedSections(assignmentAnotherPair)

            Assertions.assertThat(commonSectorsForFirstPair.first).isEmpty()
            Assertions.assertThat(commonSectorsForSecondPair.first).containsExactly(3, 4, 5, 6, 7)
            Assertions.assertThat(commonSectorsForSecondPair.second).isTrue
        }

        @Test
        fun `Should count the number of sectors where one section fully includes the other one`() {
            val sample = listOf(
                "2-4,6-8",
                "2-3,4-5",
                "5-7,7-9",
                "2-8,3-7",
                "6-6,4-6",
                "2-6,4-8")

            val totalOverlappingSectors = campCleanup.countTotalOverlappedSections(sample)

            Assertions.assertThat(totalOverlappingSectors).isEqualTo(2)
        }

        @Test
        fun `Should read sample from file and count the overlapped sections`(){
            val filePath = "src/test/resources/2022/day4"
            val sample = BufferedReader(FileReader("$filePath/sample.txt")).lines().toList()

            val totalOverlappingSectors = campCleanup.countTotalOverlappedSections(sample)

            Assertions.assertThat(totalOverlappingSectors).isEqualTo(2)
        }
    }

    @Nested
    inner class Puzzle {
        @Test
        fun `Should read puzzle from file and count the overlapped sections for all the Pair of Elves`(){
            val filePath = "src/test/resources/2022/day4"
            val sample = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines().toList()

            val totalOverlappingSectors = campCleanup.countTotalOverlappedSections(sample)

            Assertions.assertThat(totalOverlappingSectors).isEqualTo(582)
        }
    }





}
