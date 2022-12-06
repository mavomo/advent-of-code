package advent_of_code.year_2022.day5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class SupplyStacksTest {
    private val supplies = "     [D]    \n" +
            "[N] [C]    \n" +
            "[Z] [M] [P]\n" +
            " 1   2   3 \n" +
            "\n" +
            "move 1 from 2 to 1\n"

    private val supplyStacks = SupplyStacks()

    @Nested
    inner class Part1 {
        @Test
        fun `Should recognize a crate`() {
            val singleCrate = "    [D]    \n".removeSuffix("\n")
            val singleCrate2 = "[N]"

            assertThat(singleCrate.isACrate()).isTrue
            assertThat(singleCrate2.isACrate()).isTrue

        }

        @Test
        fun `Should retrieve all the columns, all crates and all the move instructions from the sample`() {
            val columnLines =
                supplies.split("\n").filter { it.isNotEmpty() && !it.isACrate() && !it.startsWith("move") }
            val crates = supplies.split("\n").filter { it.isACrate() && !it.startsWith("move") }
            val instructions = supplies.split("\n").filter { it.startsWith("move") }

            assertThat(columnLines).containsOnly(" 1   2   3 ")
            assertThat(crates).containsOnly("    [D]    ", "[N] [C]    ", "[Z] [M] [P]")
            assertThat(instructions).containsOnly("move 1 from 2 to 1")
        }

        @Test
        fun `Should arrange the column with the differents crates given as sample`() {
            val columnIndices: List<Int> = supplies.split("\n")
                .find { it.isNotEmpty() && !it.isACrate() && !it.startsWith("move") }
                ?.filter { it.digitToIntOrNull() != null }?.map { it.digitToInt() }!!

            val myColumns: MutableList<ArrayDeque<String>> = mutableListOf<ArrayDeque<String>>()
            for (index in 1..columnIndices.size) {
                val column = ArrayDeque<String>()
                myColumns.add(column)
            }

            val crates = supplies.split("\n").filter { it.isACrate() && !it.startsWith("move") }

            for (crate in crates) {
                val lines = crate.split(" ")

                for (lineIndex in 1..lines.size) {
                    val index = lineIndex - 1
                    val currentCrate = lines.get(index)
                    val associatedColumn = myColumns.get(index)
                    associatedColumn.addFirst(currentCrate)
                }
            }

            assertThat(myColumns.first()).containsExactly("[Z]", "[N]")
            assertThat(myColumns[1]).containsExactly("[M]", "[C]", "[D]")
            assertThat(myColumns.last()).containsExactly("[P]")
        }

        @Test
        fun `Should move crates given one instruction`() {
            val sample = "move 1 from 2 to 1\n"
            val instructionLine = extractInstructionsInTriplet(sample)

            val (depart, destination) = supplyStacks.moveCratesAndReturn(supplies, instructionLine)

            assertThat(depart).containsExactly("[N]")
            assertThat(destination).containsExactly("[D]", "[C]")
        }

        @Test
        fun `Should move crates given all the instructions instruction`() {
            val fullSample = " [D]    \n" +
                    "[N] [C]    \n" +
                    "[Z] [M] [P]\n" +
                    " 1   2   3 \n" +
                    "\n" +
                    "move 1 from 2 to 1\n" +
                    "move 3 from 1 to 3\n" +
                    "move 2 from 2 to 1\n" +
                    "move 1 from 1 to 2"

            val instructions: List<Triple<Int, Int, Int>> = fullSample.split("\n").filter { it.startsWith("move") }
                .flatMap { it.split("\n") }.map {
                    extractInstructionsInTriplet(it)
                }

            val finalStacks = supplyStacks.moveCratesAccordingToInstructions(fullSample, instructions)

            assertThat(finalStacks.first()).containsExactly("[C]")
            assertThat(finalStacks.get(1)).containsExactly("[M]")
            assertThat(finalStacks.last()).containsExactly("[Z]")
        }


        private fun extractInstructionsInTriplet(sample: String): Triple<Int, Int, Int> {
            val instructions = sample.filter { it.digitToIntOrNull() != null }.toCharArray().map { it.digitToInt() }
            val instructionLine = Triple(instructions.first(), instructions[1], instructions.last())
            return instructionLine
        }
    }
}


