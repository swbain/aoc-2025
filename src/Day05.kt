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

    fun isOverlap(left: Pair<Long, Long>, right: Pair<Long, Long>): Boolean {
        return when {
            left == right -> false
            left.first <= right.first -> left.second >= right.first
            else -> left.first <= right.second
        }
    }

    fun part2(input: List<String>): Long {
        val split = input.indexOfFirst { it.isBlank() }
        val rangesIn = input.take(split)

        val rangeSet = rangesIn.map {
            val split = it.split('-')
            split.first().trim().toLong() to split.last().trim().toLong()
        }.toSet()

        var hasDupes = true
        var finalRanges = mutableSetOf<Pair<Long, Long>>()
        var ranges = rangeSet
        while (hasDupes) {
            ranges.forEach { left ->
                var added = false
                val newRanges = finalRanges.toMutableSet()
                finalRanges.forEach { right ->
                    if (isOverlap(left, right)) {
                        newRanges.remove(left)
                        newRanges.remove(right)
                        newRanges.add(minOf(left.first, right.first) to maxOf(left.second, right.second))
                        added = true
                        return@forEach
                    }
                }

                if (!added) newRanges.add(left)

                finalRanges = newRanges
            }

            ranges = finalRanges
            hasDupes = ranges.any { left -> finalRanges.any { right -> isOverlap(left, right) } }
        }

        return finalRanges.sumOf { it.second - it.first + 1 }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}