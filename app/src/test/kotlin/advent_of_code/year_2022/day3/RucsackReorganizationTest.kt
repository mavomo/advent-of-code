package advent_of_code.year_2022.day3

import com.google.common.collect.ImmutableList
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileReader

internal class RucsackReorganizationTest {

    private val rucsackReorganization = RucsackReorganization()

    @Nested
    inner class Sample {
        @Nested
        inner class Part1 {
            @Test
            fun `Should count total priorities given their values`() {
                val contentOfFirstRucksack = "vJrwpWtwJgWrhcsFMMfFFhFp"
                val priorityValue = rucsackReorganization.computeSumOfAllPriorities(listOf(contentOfFirstRucksack))

                assertThat(priorityValue).isEqualTo(16)
            }

            @Test
            fun `Should find the value  of the sum of all the priroties by item type`() {
                val sample = listOf(
                    "vJrwpWtwJgWrhcsFMMfFFhFp",
                    "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
                    "PmmdzqPrVvPwwTWBwg",
                    "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
                    "ttgJtRGJQctTZtZT",
                    "CrZsJsPPZsGzwwsLwLmpwMDw"
                )
                val sumOfPriorities = rucsackReorganization.computeSumOfAllPriorities(sample)

                assertThat(sumOfPriorities).isEqualTo(157)
            }

            @Test
            fun `Should find the value  of the sum of all the priroties by item type reading the sample file`() {
                val filePath = "src/test/resources/2022/day3"
                val sample = BufferedReader(FileReader("$filePath/puzzleIn_Nico.txt")).lines().toList()

                val sumOfPriorities = rucsackReorganization.computeSumOfAllPriorities(sample)

                assertThat(sumOfPriorities).isEqualTo(157)
            }
        }

        @Nested
        inner class Part2 {
            val sample = listOf(
                "vJrwpWtwJgWrhcsFMMfFFhFp",
                "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
                "PmmdzqPrVvPwwTWBwg",
                "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
                "ttgJtRGJQctTZtZT",
                "CrZsJsPPZsGzwwsLwLmpwMDw"
            )

            @Test
            fun `Should form group of three given the sample`() {
                val commonItems = rucsackReorganization.findCommonItemsInGroupOf3(sample)

                assertThat(commonItems).hasSize(2)
                assertThat(commonItems).containsExactly('r', 'Z');
            }

            @Test
            fun `Should compute the sum of priorities given all the common item in all groups`(){
                val commonItems: ImmutableList<Char> = rucsackReorganization.findCommonItemsInGroupOf3(sample)
                val totalPriorities =  rucsackReorganization.computeSumOfPrioritiesForAllGroups(commonItems)

                assertThat(totalPriorities).isEqualTo(70)
            }
        }
    }

    @Nested
    inner class Puzzle {
        val filePath = "src/test/resources/2022/day3"
        val puzzle = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines().toList()

        @Test
        fun `Should compute the sum of priorities given the puzzle `() {
            val sumOfPriorities = rucsackReorganization.computeSumOfAllPriorities(puzzle)

            assertThat(sumOfPriorities).isEqualTo(7908)
        }

        @Test
        fun `Should compute the sum of priorities given all the common item in all groups`(){
            val commonItems: ImmutableList<Char> = rucsackReorganization.findCommonItemsInGroupOf3(puzzle)
            val totalPriorities =  rucsackReorganization.computeSumOfPrioritiesForAllGroups(commonItems)

            assertThat(totalPriorities).isEqualTo(2838)
        }

    }


}
