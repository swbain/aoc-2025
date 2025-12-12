import kotlin.system.measureTimeMillis

fun main() {

    fun String.joltage(size: Int): Long {
        return (size - 1 downTo 0).fold("" to this) { (jolts, batteries), i ->
            val max = batteries.dropLast(i).maxOf { it.digitToInt() }.digitToChar()
            val index = batteries.indexOfFirst { it == max }
            jolts + max to batteries.drop(index + 1)
        }.first.toLong()
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { it.joltage(size = 2) }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { it.joltage(size = 12) }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619L)

    val input = readInput("Day03")
    part1(input).println()
    val time = measureTimeMillis {
        part2(input).println()
    }
    println("part 2 took $time ms")
}