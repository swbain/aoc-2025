fun main() {

fun String.joltage(): Long {
    val max = dropLast(1).maxOf { it.digitToInt() }.digitToChar()
    val index = indexOfFirst { it == max }
    val sublist = drop(index + 1)
    val next = sublist.maxOf { it.digitToInt() }
    return "${max}${next}".toLong()
}

fun part1(input: List<String>): Long {
    return input.sumOf { it.joltage() }
}

fun String.joltage2(): Long {
    var answer = ""
    var line = this
    (0..11).reversed().forEach { i ->
        val max = line.dropLast(i).maxOf { it.digitToInt() }.digitToChar()
        val index = line.indexOfFirst { it == max }
        line = line.drop(index + 1)
        answer += max
    }
    return answer.toLong()
}

fun part2(input: List<String>): Long {
    return input.sumOf { it.joltage2() }
}

    val testInput = readInput("Day03_test")
//    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619L)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}