package advent_of_code.year_2022.day1

class CounterOfCalories {

    fun computeCaloriesCarriedByEachElf(caloriesMeasurements: List<String>): Map<Int, Int> {
        val allMeasurements = mutableMapOf<Int, Int>()
        var currentElfIndex = caloriesMeasurements.indexOfFirst { it.isEmpty() }.plus(1)
        var accumulator = 0
        caloriesMeasurements.forEach { rawMeasurement ->
            if (rawMeasurement.isNotEmpty() && !rawMeasurement.isLastElement(caloriesMeasurements) ) {
                accumulator += rawMeasurement.toInt()
            }else{
                val sumOfCalories = if(rawMeasurement.isLastElement(caloriesMeasurements)) rawMeasurement.toInt() else accumulator
                allMeasurements[currentElfIndex] = sumOfCalories
                accumulator = 0
                currentElfIndex++
            }
        }
        return allMeasurements
    }

    private fun String.isLastElement(caloriesMeasurements: List<String>): Boolean {
        val indexOfCurrentMeasure = caloriesMeasurements.indexOf(this)
        val lastElement = caloriesMeasurements.indexOfLast { it.isNotEmpty() }
        return indexOfCurrentMeasure==lastElement
    }

    fun getMaxCarriedCaloriesByASingleElf(allMeasurements: Map<Int, Int>): Int {
       return allMeasurements.entries.maxOf { it.value }
    }

    fun getTotalCaloriesForTheHeaviestTop3(allMeasurements: Map<Int, Int>): Any {
       val totalCaloriesForTheTopThree = allMeasurements.entries.sortedByDescending { it.value }
        return totalCaloriesForTheTopThree.take(3).sumOf { it.value }
    }
}
