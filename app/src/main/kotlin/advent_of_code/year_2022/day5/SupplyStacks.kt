package advent_of_code.year_2022.day5

class SupplyStacks {

    fun moveCratesAndReturn(
        puzzle: String,
        instructionLine: Triple<Int, Int, Int>
    ): Pair<ArrayDeque<String>, ArrayDeque<String>> {
        val crates = puzzle.split("\n").filter { it.isACrate() && !it.startsWith("move") }
        val startingStacks: List<ArrayDeque<String>> = buildStackWithTheirCrates(crates)

        val depart = startingStacks.get(instructionLine.second - 1)
        val nbCrateToRemove = instructionLine.first;
        val destination = startingStacks[instructionLine.third - 1]
        for (c in 1..nbCrateToRemove) {
            val removedElement = depart.removeLast()
            destination.add(removedElement)
        }
        return Pair(depart, destination)
    }

    fun buildStackWithTheirCrates(crates: List<String>) = crates.map { crate ->
        val currentColumn = ArrayDeque<String>()
        val crateIterator = crate.split(" ").iterator()
        if (crateIterator.hasNext()) {
            if (crate.isACrate()) {
                currentColumn.addAll(crate.trim().split(" "))
            }
        }
        currentColumn
    }

    fun moveCratesAccordingToInstructions(
        supplies: String,
        allInstructions: List<Triple<Int, Int, Int>>
    ): List<ArrayDeque<String>> {
        val crates = supplies.split("\n").filter { it.isACrate() && !it.startsWith("move") }
        val stacks: List<ArrayDeque<String>> = buildStackWithTheirCrates(crates)

        for (instruction in allInstructions) {
            val depart = stacks.get(instruction.second - 1)
            val nbCrateToRemove = instruction.first;
            val destination = stacks[instruction.third - 1]
            for (c in 1..nbCrateToRemove) {
                if (depart.isNotEmpty()) {
                    val removedElement = depart.removeLast()
                    destination.add(removedElement)
                }

            }
        }
        return stacks
    }

}

fun String.isACrate(): Boolean {
    val pattern = "^(.*\\[([A-Z]*)\\].*)+\$"
    return this.matches(Regex(pattern))
}
