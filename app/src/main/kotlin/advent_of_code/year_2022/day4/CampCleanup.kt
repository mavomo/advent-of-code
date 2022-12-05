package advent_of_code.year_2022.day4

class CampCleanup {

    fun countTotalFullyOverlapingSections(puzzle: List<String>): Int {
        var totalOverlappingSectors = 0
        puzzle.forEach {
            val commonSector = getCommonAssignedSections(it)
            if (commonSector.second) {
                totalOverlappingSectors++
            }
        }
        return totalOverlappingSectors
    }

    fun countTotalPartiallyOverlapingSections(puzzle: List<String>): Int {
        var partialOverlappingSectors = 0
        puzzle.forEach {
            val commonSector = getCommonAssignedSections2(it)
            if (commonSector.first.isNotEmpty()) {
                partialOverlappingSectors++
            }
        }
        return partialOverlappingSectors
    }

    fun getCommonAssignedSections(assignment: String): Pair<Set<Int>, Boolean> {
        val assignmentPerGroup = assignment.split(",")
        val firstPairSection = assignmentPerGroup.first().split("-").map { it.toInt() }
        val secondPairSection = assignmentPerGroup.last().split("-").map { it.toInt() }

        val fullSectorForFirstGroup = (firstPairSection.first()..firstPairSection.last()).toSet()
        val fullSectorForTheOtherGroup = (secondPairSection.first()..secondPairSection.last()).toSet()

        val commonSectors = fullSectorForTheOtherGroup.intersect(fullSectorForFirstGroup)

        val isFullyContained =
            fullSectorForFirstGroup.containsAll(fullSectorForTheOtherGroup) || fullSectorForTheOtherGroup.containsAll(
                fullSectorForFirstGroup
            )
        return Pair(commonSectors, isFullyContained)
    }

    fun getCommonAssignedSections2(assignment: String): Pair<Set<Int>, Boolean> {
        val assignmentPerGroup = assignment.split(",")
        val firstPairSection = assignmentPerGroup.first().split("-").map { it.toInt() }
        val secondPairSection = assignmentPerGroup.last().split("-").map { it.toInt() }

        val fullSectorForFirstGroup = (firstPairSection.first()..firstPairSection.last()).toSet()
        val fullSectorForTheOtherGroup = (secondPairSection.first()..secondPairSection.last()).toSet()

        val commonSectors = fullSectorForTheOtherGroup.intersect(fullSectorForFirstGroup)

        /* val isFullyContained =
             fullSectorForFirstGroup.containsAll(fullSectorForTheOtherGroup) || fullSectorForTheOtherGroup.containsAll(
                 fullSectorForFirstGroup
             )*/
        return Pair(commonSectors, false)
    }


}
