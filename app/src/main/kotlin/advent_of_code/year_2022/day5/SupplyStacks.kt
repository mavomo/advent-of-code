package advent_of_code.year_2022.day5

import com.google.common.collect.ImmutableList

class SupplyStacks {

    fun moveCratesAccordingToInstructions(
        allInstructions: List<Triple<Int, Int, Int>>,
        stacks: MutableList<ArrayDeque<String>>
    ): List<ArrayDeque<String>> {
        for (instruction in allInstructions) {
            val depart = stacks[instruction.second - 1]
            val nbCrateToRemove = instruction.first;
            val destination = stacks[instruction.third - 1]
            for (c in 1..nbCrateToRemove) {
                if (depart.isNotEmpty()) {
                    val removedElement = depart.removeLast()
                    destination.addLast(removedElement)
                }

            }
        }
        return stacks
    }

    fun addCratesPerStack(lineInput: List<String>, columnIndices: List<Int>): ImmutableList<ArrayDeque<String>>? {
        val myColumns: MutableList<ArrayDeque<String>> = createDefaultStateOfStacks(columnIndices)

        for (line in lineInput) {
            val myCrateAsLine: List<String> = line.split(" ")
            val lower =  myCrateAsLine.subList(0, (myCrateAsLine.size /2)-1)
            val upper = myCrateAsLine.subList((myCrateAsLine.size / 2), myCrateAsLine.size)
            val theCrate = myCrateAsLine.find { it.isACrate() }!!
            var idxForInsertion = 0
            if (upper.contains(theCrate)) {
                idxForInsertion++
                val rightColumn = myColumns[idxForInsertion]
                rightColumn.addFirst(theCrate)
            } else {
                for (crateIdx in myCrateAsLine.indices) {
                    val currentCrate = myCrateAsLine[crateIdx]
                    if (currentCrate.isACrate()) {
                        val rightColumn = myColumns[crateIdx]
                        rightColumn.addFirst(currentCrate)
                    }
                }
            }
        }
        return ImmutableList.copyOf(myColumns)
    }

    private fun createDefaultStateOfStacks(columnIndices: List<Int>): MutableList<ArrayDeque<String>> {
        val myColumns: MutableList<ArrayDeque<String>> = mutableListOf()
        for (index in 1..columnIndices.size) {
            val column = ArrayDeque<String>()
            myColumns.add(column)
        }
        return myColumns
    }

}

fun String.isACrate(): Boolean {
    val pattern = "^(.*\\[([A-Z]*)\\].*)+\$"
    return this.matches(Regex(pattern))
}

fun String.retrieveStackNumbers(): List<Int> {
   return this.split("\n")
       .find { it.isNotEmpty() && !it.isACrate() && !it.startsWith("move") }
       ?.filter { it.digitToIntOrNull() != null }?.map { it.digitToInt() }!!
}

fun String.retrieveAllCratesPerStack(): List<String>  = this.split("\n").filter { it.isACrate() && !it.startsWith("move") }


fun String.retrieveAllTheInstruction(): List<String> = this.split("\n").filter { it.startsWith("move") }

fun String.extractInstructionsInTriplet(it: String): Triple<Int, Int, Int> {
    val instructions = this.filter { it.digitToIntOrNull() != null }.toCharArray().map { it.digitToInt() }
    return Triple(instructions.first(), instructions[1], instructions.last())
}

fun String.getInstructionsAsTriplet(puzzle: String): List<Triple<Int, Int, Int>> = this.split("\n").filter { it.startsWith("move") }
    .flatMap { it.split("\n") }.map {
        it.extractInstructionsInTriplet(it)
    }
