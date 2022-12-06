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

    fun moveCratesWithCrateMover9001(
        instructions: List<Triple<Int, Int, Int>>,
        stacks: MutableList<ArrayDeque<String>>
    ): ImmutableList<ArrayDeque<String>>? {
        for (instruction in instructions) {
            val depart = stacks[instruction.second - 1]
            val nbCrateToRemove = instruction.first;
            val destination = stacks[instruction.third - 1]
            if(nbCrateToRemove==1){
                for (c in 1..nbCrateToRemove) {
                    if (depart.isNotEmpty()) {
                        val removedElement = depart.removeLast()
                        destination.addLast(removedElement)
                    }
                }
            }else {
                if(depart.isNotEmpty()){
                    val orderedCrates = depart.take(nbCrateToRemove)
                    destination.addAll(orderedCrates)
                    depart.removeAll(orderedCrates)
                }
            }

        }
        return ImmutableList.copyOf(stacks)
    }

    fun addCratesPerStack(lineInput: List<String>, columnIndices: List<Int>): ImmutableList<ArrayDeque<String>>? {
        val myColumns: MutableList<ArrayDeque<String>> = createDefaultStateOfStacks(columnIndices)
        for (line in lineInput) {
            val lineIterator = line.iterator()
            var lastColumn = 0
            var lastWhitespace = 0
            val currentCrate: StringBuilder = java.lang.StringBuilder()
            while (lineIterator.hasNext()) {
                val currentValue = lineIterator.next()
                if (!currentValue.isWhitespace() && currentCrate.length <= 3) {
                    currentCrate.append(currentValue)
                    if (currentCrate.length == 3) {
                        val column = myColumns[lastColumn]
                        column.addFirst(currentCrate.toString())
                        lastColumn++
                        currentCrate.clear()
                        lastWhitespace = 0
                    }
                }
                if (currentValue.isWhitespace() && currentCrate.isEmpty()) {
                    lastWhitespace++
                    if(lastWhitespace >= 4 ){
                        lastWhitespace = 0
                        lastColumn++
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

fun String.retrieveAllCratesPerStack(): List<String> =
    this.split("\n").filter { it.isACrate() && !it.startsWith("move") }


fun String.retrieveAllTheInstruction(): List<String> = this.split("\n").filter { it.startsWith("move") }

fun String.extractInstructionsInTriplet(): Triple<Int, Int, Int> {
    val instructions = this.filter { !it.isLetter() }.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
    return Triple(instructions.first(), instructions[1], instructions.last())
}

fun String.getInstructionsAsTriplet(): List<Triple<Int, Int, Int>> = this.split("\n").filter { it.startsWith("move") }
    .flatMap { it.split("\n") }.map {
        it.extractInstructionsInTriplet()
    }
