package advent_of_code.year_2022.day3

import com.google.common.collect.ImmutableList

class RucsackReorganization {
    private val alphabetsInLowerCase = ('a'..'z').toSet()
    private val alphabetsInUpperCase = ('A'..'Z').toSet()

    fun computeSumOfAllPriorities(sample: List<String>): Any {
        var totalPriorities = 0
        val itemsByPriorities = itemsTypeByPriorities()

        for (it in sample) {
            val commonItem = findCommonItems(it).first()
            totalPriorities += if (commonItem.isLowerCase()) {
                itemsByPriorities.first.get(commonItem)!!
            }else {
                itemsByPriorities.second.get(commonItem)!!
            }
        }
        return totalPriorities
    }

     fun findCommonItems(content: String): Set<Char> {
        val contentByCompartiment: List<String> = content.chunked(content.length / 2)
        val contentOfFirstCompartiment = contentByCompartiment.first().toCharArray()
        val contentOfSecondCompartiment = contentByCompartiment.last().toCharArray()
        return contentOfFirstCompartiment.intersect(contentOfSecondCompartiment.toList())
    }

    fun itemsTypeByPriorities(): Pair<Map<Char, Int>, Map<Char, Int>> {
        val lowercaseWithPriorities: Map<Char, Int> = alphabetsInLowerCase.mapIndexed { index, currentItem ->
            Pair(currentItem, index + 1)
        }.toMap()

        var initialIdx = 26
        val uppercaseWithPriorities: Map<Char, Int> = alphabetsInUpperCase.map { currrentItem ->
            initialIdx++
            Pair(currrentItem, initialIdx)
        }.toMap()
        return Pair(lowercaseWithPriorities, uppercaseWithPriorities)
    }

    fun findCommonItemsInGroupOf3(items: List<String>): ImmutableList<Char> {
        val nbGroups = items.chunked(3)
        val commonItems: MutableList<Char> = mutableListOf()
        nbGroups.forEach { itemsOfGroup ->
            val firstGroup = itemsOfGroup.first().toCharArray()
            val secondGroup = itemsOfGroup.get(1).toCharArray()
            val thirdGroup = itemsOfGroup.last().toCharArray()
            val common = firstGroup.intersect(secondGroup.toList()).intersect(thirdGroup.toList())
            commonItems.add(common.first())
        }
        return ImmutableList.copyOf(commonItems)

    }

    fun computeSumOfPrioritiesForAllGroups(commonItems: ImmutableList<Char>): Any {
       val typesByPriority = itemsTypeByPriorities()
        var totalPriorities = 0
        commonItems.forEach{
            totalPriorities += if(it.isLowerCase()){
                typesByPriority.first.get(it)!!
            }else {
                typesByPriority.second.get(it)!!
            }
        }
        return totalPriorities
    }
}
