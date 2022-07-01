/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package advent.of.code

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
}


