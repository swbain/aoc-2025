fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 1)

    1 shl 4

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}