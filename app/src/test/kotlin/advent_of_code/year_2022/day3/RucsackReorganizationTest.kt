package advent_of_code.year_2022.day3

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class RucsackReorganizationTest {

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

        Assertions.assertThat(commonItemInFirstRucksack).containsOnly('p')
        Assertions.assertThat(commonItemInSecondRucksack).containsOnly('L')
        Assertions.assertThat(commonItemInThirdRucksack).containsOnly('P')
        Assertions.assertThat(commonItemInFourthRucksack).containsOnly('v')
        Assertions.assertThat(commonItemInFifthRucksack).containsOnly('t')
        Assertions.assertThat(commonItemInSithRucksack).containsOnly('s')
    }

    private fun findCommonItems(content: String): Set<Char> {
        val contentByCompartiment: List<String> = content.chunked(content.length / 2)
        val contentOfFirstCompartiment = contentByCompartiment.first().toCharArray()
        val contentOfSecondCompartiment = contentByCompartiment.last().toCharArray()
        return contentOfFirstCompartiment.intersect(contentOfSecondCompartiment.toList())
    }
}
