import kotlin.system.measureTimeMillis

fun main() {

    fun String.joltage(size: Int): Long {
        var jolts = ""
        var batteries = this
        for (i in size - 1 downTo 0) {
            val max = batteries.dropLast(i).maxOf { it.digitToInt() }.digitToChar()
            val index = batteries.indexOfFirst { it == max }
            batteries = batteries.drop(index + 1)
            jolts += max
        }
        return jolts.toLong()
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