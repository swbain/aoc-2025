fun main() {
    fun part1(input: List<String>): Long {
        return input.reduce { acc, string -> acc + string }
            .split(',')
            .asSequence()
            .flatMap {
                val split = it.split('-')
                LongRange(split.first().toLong(), split.last().toLong())
            }
            .map { "$it" }
            .filter {
                if (it.length % 2 != 0) false
                else {
                    val half = it.length / 2
                    val start = it.take(half)
                    val end = it.drop(half)
                    start == end
                }
            }.sumOf { it.toLong() }
    }

    fun part2(input: List<String>): Long {
        return input.reduce { acc, string -> acc + string }
            .split(',')
            .asSequence()
            .flatMap {
                val split = it.split('-')
                LongRange(split.first().toLong(), split.last().toLong())
            }
            .map { "$it" }
            .filter {
                (1..it.length / 2).forEach { length ->
                    val groups = it.length / length
                    val pattern = it.take(length)
                    if (it == pattern.repeat(groups)) return@filter true
                }
                false
            }.sumOf { it.toLong() }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 4174379265L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}