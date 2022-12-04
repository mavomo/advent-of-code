package advent_of_code.year_2022.day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class RucsackReorganizationTest {
    private val alphabetsInLowerCase = ('a'..'z').toSet()
    private val alphabetsInUpperCase = ('A'..'Z').toSet()

    @Test
    fun `should find the common item in both compartments `() {
        val contentOfFirstRucksack = "vJrwpWtwJgWrhcsFMMfFFhFp"
        val contentOfSecondRucksack = "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
        val contentOfThirdRucksack = "PmmdzqPrVvPwwTWBwg"
        val contentOfFourthRucksack = "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
        val contentOfFifthRucksack = "ttgJtRGJQctTZtZT"
        val contentOfSixthRucksack = "CrZsJsPPZsGzwwsLwLmpwMDw"

        val commonItemInFirstRucksack = findCommonItems(contentOfFirstRucksack)
        val commonItemInSecondRucksack = findCommonItems(contentOfSecondRucksack)
        val commonItemInThirdRucksack = findCommonItems(contentOfThirdRucksack)
        val commonItemInFourthRucksack = findCommonItems(contentOfFourthRucksack)
        val commonItemInFifthRucksack = findCommonItems(contentOfFifthRucksack)
        val commonItemInSithRucksack = findCommonItems(contentOfSixthRucksack)

        assertThat(commonItemInFirstRucksack).containsOnly('p')
        assertThat(commonItemInSecondRucksack).containsOnly('L')
        assertThat(commonItemInThirdRucksack).containsOnly('P')
        assertThat(commonItemInFourthRucksack).containsOnly('v')
        assertThat(commonItemInFifthRucksack).containsOnly('t')
        assertThat(commonItemInSithRucksack).containsOnly('s')
    }

    @Test
    fun `Should count total priorities given their values`() {

        val lowercaseWithPriorities: Map<Char, Int> = alphabetsInLowerCase.mapIndexed { index, currentItem ->
            Pair(currentItem, index + 1)
        }.toMap()

        var initialIdx = 26
        val uppercaseWithPriorities: Map<Char, Int> = alphabetsInUpperCase.map { currrentItem ->
            initialIdx++
            Pair(currrentItem, initialIdx)
        }.toMap()

        val contentOfFirstRucksack = "vJrwpWtwJgWrhcsFMMfFFhFp"
        val commonItemInFirstRucksack = findCommonItems(contentOfFirstRucksack).first()
        var prio = 0
        if (commonItemInFirstRucksack.isLowerCase()) {
            prio += lowercaseWithPriorities.get(commonItemInFirstRucksack)!!
        }

        assertThat(prio).isEqualTo(16)
    }

    @Test
    fun `Should do something`() {
        val sample = listOf(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw"
        )
        var priorities = 0
        val itemsByPriorities = buildItemsTypeWithPriorities()

        sample.forEach {
            val commonItem = findCommonItems(it).first()
            if (commonItem.isLowerCase()) {
                priorities += itemsByPriorities.first.get(commonItem)!!
            }else {
                priorities += itemsByPriorities.second.get(commonItem)!!
            }
        }
        assertThat(priorities).isEqualTo(157)
    }

    private fun buildItemsTypeWithPriorities(): Pair<Map<Char, Int>, Map<Char, Int>> {
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

    private fun findCommonItems(content: String): Set<Char> {
        val contentByCompartiment: List<String> = content.chunked(content.length / 2)
        val contentOfFirstCompartiment = contentByCompartiment.first().toCharArray()
        val contentOfSecondCompartiment = contentByCompartiment.last().toCharArray()
        return contentOfFirstCompartiment.intersect(contentOfSecondCompartiment.toList())
    }
}
