package advent_of_code.year_2021

class AdventOfCode2021 {

    fun countingDepthMeasurementIncreases(report: List<Int>): Int {
        var increaseCounter = 0
        for (depth in report.withIndex()) {
            val depthValue = depth.value
            val nextIdx = depth.index + 1
            if (nextIdx < report.size && depthValue < report[nextIdx]) {
                increaseCounter++
            }
        }
        return increaseCounter
    }

    fun countingIncresalOfMeasurements(measurementsBySlidingWindow: Map<Char, List<Int>>): Int {
        val sumByWindow: Map<Char, Int> = measurementsBySlidingWindow.entries.associate { it.key to it.value.sum() }
        var numberOfIncrease = 0
        var previousValue = sumByWindow.entries.iterator().next()
        sumByWindow.entries.filterNot { it.key == previousValue.key }
            .forEach {
                if (it.value > previousValue.value) {
                    numberOfIncrease++
                }
                previousValue = it
            }
        return numberOfIncrease
    }

    fun makeListOfThreeSlidingWindowData(measures: List<Int>, chunk: Int): MutableList<Int> {
        val listWithSum = mutableListOf<Int>()
        val maxSum = measures.subList(0, chunk).sum()
        listWithSum.add(maxSum)
        var i = chunk
        var windowSum = maxSum
        while (i < measures.size) {
            windowSum += measures[i] - measures[i - chunk]
            listWithSum.add(windowSum)
            i++
        }
        return listWithSum
    }

    fun getSubmarinPosition(instructions: List<String>): Pair<Int, Int> {
        var horizontal = 0
        var depth = 0
        instructions.forEach {
            val instruction = it.split(" ")
            val command = instruction[0]
            val distance = instruction[1].toInt()
            when (command) {
                "forward" -> horizontal += distance
                "down" -> depth += distance
                "up" -> depth -= distance
            }
        }
        return Pair(depth, horizontal)
    }


}


