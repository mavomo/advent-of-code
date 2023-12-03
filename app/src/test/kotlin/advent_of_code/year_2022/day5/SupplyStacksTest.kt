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
        fun `Should read instructions with 2 digit`() {
            val basicInstruction = "move 13 from 8 to 7"
            val instructions = basicInstruction.extractInstructionsInTriplet()

            assertThat(instructions).isEqualTo(Triple(13,8,7))
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
            val sample = BufferedReader(FileReader("$filePath/puzzleIn_Nico.txt")).lines().toList().joinToString("\n")
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

        @Test
        fun `Should arrange crates given the second sample`() {
            val filePath = "src/test/resources/2022/day5"
            val sample = BufferedReader(FileReader("$filePath/sample-2.txt")).lines().toList().joinToString("\n")

            val columnIndices: List<Int> = sample.retrieveStackNumbers()
            val crates = sample.retrieveAllCratesPerStack()

            val myColumnsWithCrates = supplyStacks.addCratesPerStack(crates, columnIndices)
            assertThat(myColumnsWithCrates!!.first()).containsExactly("[J]", "[F]", "[N]", "[V]")
            assertThat(myColumnsWithCrates[1]).containsExactly("[S]", "[D]", "[Q]")
            assertThat(myColumnsWithCrates[2]).containsExactly("[N]", "[S]", "[W]", "[B]")
            assertThat(myColumnsWithCrates[3]).containsExactly("[R]")
            assertThat(myColumnsWithCrates[4]).containsExactly("[M]", "[B]")
            assertThat(myColumnsWithCrates[5]).containsExactly("[T]")
            assertThat(myColumnsWithCrates[6]).containsExactly("[G]")
            assertThat(myColumnsWithCrates[7]).containsExactly("[C]", "[L]", "[R]")
            assertThat(myColumnsWithCrates[8]).containsExactly("[D]", "[P]", "[B]", "[F]")
        }


        @Test
        fun `Should keep the crates order when using the moving crate 9001`(){
            val filePath = "src/test/resources/2022/day5"
            val sample = BufferedReader(FileReader("$filePath/sample-3.txt")).lines().toList().joinToString("\n")


            val instructions: List<Triple<Int, Int, Int>> = sample.getInstructionsAsTriplet()


            val columnIndices: List<Int> = sample.retrieveStackNumbers()
            val crates = sample.retrieveAllCratesPerStack()

            val myColumnsWithCrates = supplyStacks.addCratesPerStack(crates, columnIndices)
            val finalStacksWithCrateMover9001 = supplyStacks.moveCratesWithCrateMover9001(instructions, myColumnsWithCrates!!.toMutableList())

            assertThat(finalStacksWithCrateMover9001!!.first()).containsExactly("[M]")
            assertThat(finalStacksWithCrateMover9001[1]).containsExactly("[C]")
            assertThat(finalStacksWithCrateMover9001[2]).containsExactly("[P]","[Z]", "[N]", "[D]")
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

            val topOfStacks = finalStacks.map { it.last() }
            assertThat(topOfStacks).containsExactly("[S]", "[B]", "[P]", "[Q]", "[R]", "[S]", "[C]", "[D]", "[F]")
        }

        @Test
        fun `Should get the crates on top of the stack using the CrateMover *9001*`(){
            val filePath = "src/test/resources/2022/day5"
            val sample = BufferedReader(FileReader("$filePath/puzzleInput.txt")).lines().toList().joinToString("\n")
            val instructions: List<Triple<Int, Int, Int>> = sample.getInstructionsAsTriplet()

            val columnIndices: List<Int> = sample.retrieveStackNumbers()
            val crates = sample.retrieveAllCratesPerStack()

            val myColumnsWithCrates = supplyStacks.addCratesPerStack(crates, columnIndices)
            val finalStacksWithCrateMover9001 = supplyStacks.moveCratesWithCrateMover9001(instructions, myColumnsWithCrates!!.toMutableList())


            val topOfStacks = finalStacksWithCrateMover9001!!.map { it.last() }

            //RGLVRCQSB
            assertThat(topOfStacks).containsExactly("[R]", "[G]", "[L]", "[V]", "[R]", "[C]", "[Q]", "[S]", "[B]")
        }

        @Test
        fun `Should keep the crates order when using the moving crate 9001 -- Nicolas`(){
            val filePath = "src/test/resources/2022/day5"
            val sample = BufferedReader(FileReader("$filePath/puzzleNico.txt")).lines().toList().joinToString("\n")
            val instructions: List<Triple<Int, Int, Int>> = sample.getInstructionsAsTriplet()

            val columnIndices: List<Int> = sample.retrieveStackNumbers()
            val crates = sample.retrieveAllCratesPerStack()

            val myColumnsWithCrates = supplyStacks.addCratesPerStack(crates, columnIndices)
            val finalStacksWithCrateMover9001 =
                supplyStacks.moveCratesWithCrateMover9001(instructions, myColumnsWithCrates!!.toMutableList())

           // assertThat(finalStacksWithCrateMover9001!!.last()).containsExactly("[P]", "[Z]", "[N]", "[D]")

            val topOfStacks = finalStacksWithCrateMover9001!!.map { it.last() }


            //PQTJRSHWS
            assertThat(topOfStacks).containsExactly("[P]", "[Q]", "[T]", "[J]","[R]", "[S]", "[H]", "[W]", "[S]" )
        }

        @Test
        fun `Should arrange crates given Nico sample`() {
            val filePath = "src/test/resources/2022/day5"
            val sample = BufferedReader(FileReader("$filePath/puzzleNico.txt")).lines().toList().joinToString("\n")
            val instructions: List<Triple<Int, Int, Int>> = sample.getInstructionsAsTriplet()

            val columnIndices: List<Int> = sample.retrieveStackNumbers()
            val crates = sample.retrieveAllCratesPerStack()

            val myColumnsWithCrates = supplyStacks.addCratesPerStack(crates, columnIndices)
            val finalStacks = supplyStacks.moveCratesAccordingToInstructions(instructions, myColumnsWithCrates!!.toMutableList())
            val topOfStacks = finalStacks.map { it.last() }

            assertThat(topOfStacks).containsExactly("[H]", "[B]", "[T]", "[M]", "[T]", "[B]", "[S]", "[D]", "[C]")
        }

    }

}










