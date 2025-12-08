fun main() {

    fun part1(input: List<String>): Int {
        val split = input.indexOfFirst { it.isBlank() }
        val ranges = input.take(split)
        val ingredients = input.drop(split + 1)

        val rangeSet = ranges.map {
            val split = it.split('-')
            split.first().trim().toLong() to split.last().trim().toLong()
        }.toSet()

        return ingredients.map { it.toLong() }.count { ingredient ->
            rangeSet.find { it.first <= ingredient && it.second >= ingredient } != null
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
//    part2(testInput).println()
//    check(part2(testInput) == 14)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}