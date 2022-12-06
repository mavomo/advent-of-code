package advent_of_code.year_2022.day5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileReader

internal class SupplyStacksTest {
    private val supplies = "     [D]    \n" +
            "[N] [C]    \n" +
            "[Z] [M] [P]\n" +
            " 1   2   3 \n" +
            "\n" +
            "move 1 from 2 to 1\n"

    private val supplyStacks = SupplyStacks()

    @Nested
    inner class Sample {
        @Test
        fun `Should recognize a crate`() {
            val singleCrate = "    [D]    \n".removeSuffix("\n")
            val singleCrate2 = "[N]"

            assertThat(singleCrate.isACrate()).isTrue
            assertThat(singleCrate2.isACrate()).isTrue

        }

        @Test
        fun `Should retrieve all the columns, all crates and all the move instructions from the sample`() {
            val columnLines = supplies.retrieveStackNumbers()
            val crates = supplies.retrieveAllCratesPerStack()
            val instructions = supplies.retrieveAllTheInstruction()

            assertThat(columnLines).containsOnly(1, 2, 3)
            assertThat(crates).containsOnly("     [D]    ", "[N] [C]    ", "[Z] [M] [P]")
            assertThat(instructions).containsOnly("move 1 from 2 to 1")
        }

        @Test
        fun `Should arrange each crate in their stack given the sample`() {
            val input = "     [D]    \n" +
                    "[N] [C]    \n" +
                    "[Z] [M] [P]\n" +
                    " 1   2   3 \n"
            val columnLines: List<Int> = input.retrieveStackNumbers()

            val lineInput = input.retrieveAllCratesPerStack()
            val myColumnsWithCrates = supplyStacks.addCratesPerStack(lineInput, columnLines)

            assertThat(myColumnsWithCrates!!.first()).containsExactly("[Z]", "[N]")
            assertThat(myColumnsWithCrates[1]).containsExactly("[M]", "[C]", "[D]")
            assertThat(myColumnsWithCrates.last()).containsExactly("[P]")
        }


        @Test
        fun `Should arrange the column with the different crates given as sample`() {
            val columnIndices: List<Int> = supplies.retrieveStackNumbers()
            val crates = supplies.retrieveAllCratesPerStack()

            val myColumnsWithCrates = supplyStacks.addCratesPerStack(crates, columnIndices)

            assertThat(myColumnsWithCrates!!.first()).containsExactly("[Z]", "[N]")
            assertThat(myColumnsWithCrates[1]).containsExactly("[M]", "[C]", "[D]")
            assertThat(myColumnsWithCrates.last()).containsExactly("[P]")
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

            val instructions: List<Triple<Int, Int, Int>> = fullSample.getInstructionsAsTriplet()
            val columnIndices: List<Int> = supplies.retrieveStackNumbers()
            val crates = supplies.retrieveAllCratesPerStack()

            val myColumnsWithCrates = supplyStacks.addCratesPerStack(crates, columnIndices)
            val finalStacks =
                supplyStacks.moveCratesAccordingToInstructions(instructions, myColumnsWithCrates!!.toMutableList())

            assertThat(finalStacks.first()).containsExactly("[C]")
            assertThat(finalStacks[1]).containsExactly("[M]")
            assertThat(finalStacks.last()).containsExactly("[P]", "[D]", "[N]", "[Z]")

            val topOfStacks = finalStacks.map { it.last() }
            assertThat(topOfStacks).containsExactly("[C]", "[M]", "[Z]")
        }

        @Test
        fun `Should move crates given the instructions from the sample file`() {
            val filePath = "src/test/resources/2022/day5"
            val sample = BufferedReader(FileReader("$filePath/sample.txt")).lines().toList().joinToString("\n")
            val instructions: List<Triple<Int, Int, Int>> = sample.getInstructionsAsTriplet()

            val columnIndices: List<Int> = supplies.retrieveStackNumbers()
            val crates = supplies.retrieveAllCratesPerStack()

            val myColumnsWithCrates = supplyStacks.addCratesPerStack(crates, columnIndices)
            val finalStacks =
                supplyStacks.moveCratesAccordingToInstructions(instructions, myColumnsWithCrates!!.toMutableList())

            assertThat(finalStacks.first()).containsExactly("[C]")
            assertThat(finalStacks[1]).containsExactly("[M]")
            assertThat(finalStacks.last()).containsExactly("[P]", "[D]", "[N]", "[Z]")

            val topOfStacks = finalStacks.map { it.last() }
            assertThat(topOfStacks).containsExactly("[C]", "[M]", "[Z]")
        }


    }

    @Nested
    inner class Puzzle {

        @Test
        fun `Should move crates given the instructions from the puzzle file`() {
            val filePath = "src/test/resources/2022/day5"
            val puzzle = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines().toList().joinToString("\n")
            val instructions: List<Triple<Int, Int, Int>> = puzzle.getInstructionsAsTriplet()

            val columnIndices: List<Int> = puzzle.retrieveStackNumbers()
            val crates = puzzle.retrieveAllCratesPerStack()

            val myColumnsWithCrates = supplyStacks.addCratesPerStack(crates, columnIndices)
            val finalStacks =
                supplyStacks.moveCratesAccordingToInstructions(instructions, myColumnsWithCrates!!.toMutableList())

            assertThat(finalStacks.first()).containsExactly("[C]")
            assertThat(finalStacks[1]).containsExactly("[M]")
            assertThat(finalStacks.last()).containsExactly("[P]", "[D]", "[N]", "[Z]")

            val topOfStacks = finalStacks.map { it.last() }
            assertThat(topOfStacks).containsExactly("[C]", "[M]", "[Z]")
        }
    }

}










