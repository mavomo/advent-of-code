package advent_of_code.year_2022.day5

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class SupplyStacksTest {
    private val supplies = "    [D]    \n" +
            "[N] [C]    \n" +
            "[Z] [M] [P]\n" +
            " 1   2   3 \n" +
            "\n" +
            "move 1 from 2 to 1\n"

    @Test
    fun `Should recognize a crate`() {
        val singleCrate = "    [D]    \n".removeSuffix("\n")
        val singleCrate2 = "[N]"

        Assertions.assertThat(singleCrate.isACrate()).isTrue
        Assertions.assertThat(singleCrate2.isACrate()).isTrue

    }

    @Test
    fun `Should retrieve all the columns, all crates and all the move instructions from the sample`() {
        val columnLines = supplies.split("\n").filter { it.isNotEmpty() && !it.isACrate() && !it.startsWith("move") }
        val crates = supplies.split("\n").filter { it.isACrate() && !it.startsWith("move") }
        val instructions = supplies.split("\n").filter { it.startsWith("move") }

        Assertions.assertThat(columnLines).containsOnly(" 1   2   3 ")
        Assertions.assertThat(crates).containsOnly("    [D]    ", "[N] [C]    ", "[Z] [M] [P]")
        Assertions.assertThat(instructions).containsOnly("move 1 from 2 to 1")
    }

    @Test
    fun `Should arrange the column with the differents crates given as sample`() {
        val columnAsString: String? = supplies.split("\n").find { it.isNotEmpty() && !it.isACrate() && !it.startsWith("move") }
        val allColumns = columnAsString!!.split("   ")
        val crates = supplies.split("\n").filter { it.isACrate() && !it.startsWith("move") }
        val startingStacks  = buildStackWithTheirCrates(crates)


        Assertions.assertThat(allColumns).hasSize(3)
        Assertions.assertThat(startingStacks.first()).hasSize(1)
        Assertions.assertThat(startingStacks.first()).containsExactly("[D]")
        Assertions.assertThat(startingStacks[1]).containsExactly("[N] [C]")
        Assertions.assertThat(startingStacks.last()).containsExactly("[Z] [M] [P]")

    }

    private fun buildStackWithTheirCrates(crates: List<String>) = crates.map { crate ->
        val currentColumn = ArrayDeque<String>()
        val crateIterator = crate.split(" ").iterator()
        if (crateIterator.hasNext()) {
            if (crate.isACrate()) {
                currentColumn.add(crate.trim())
            }
        }
        currentColumn
    }


}

private fun String.isACrate(): Boolean {
    val pattern = "^(.*\\[([A-Z]*)\\].*)+\$"
    return this.matches(Regex(pattern))
}
