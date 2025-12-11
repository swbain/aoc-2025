import utils.Grid
import utils.Pos

fun main() {
    fun part1(input: List<String>): Int {
        var output = ""
        var splits = 0
        input.forEachIndexed { i, value ->
            if (i == 0) output = value.replace('S', '|')
            else {
                val currentLine = value.toCharArray()
                for (index in 0..<currentLine.count()) {
                    val previousChar = output[index]
                    val currentChar = currentLine[index]
                    if (previousChar == '|') {
                        if (currentChar == '^') {
                            currentLine[index - 1] = '|'
                            currentLine[index] = '.'
                            currentLine[index + 1] = '|'
                            splits++
                        } else {
                            currentLine[index] = '|'
                        }
                    } else {
                        if (currentLine[index] != '|') currentLine[index] = '.'
                    }
                }
                output = currentLine.concatToString()
            }
        }
        return splits
    }

    val splits = setOf(Pos.LEFT, Pos.RIGHT)

    fun <I, O> memoize(function: ((I) -> O).(I) -> O): (I) -> O = object : (I) -> O {
        val cache = hashMapOf<I, O>()
        override fun invoke(input: I): O = cache.getOrPut(input) { function(input) }
    }

    fun part2(input: List<String>): Long {
        val grid = Grid(input)
        val start = grid.data.flatten().first { it.value == 'S' }.pos
        val solution = memoize<Pos, Long> { pos ->
            when {
                !grid.contains(pos) -> 1L
                grid[pos].value == '^' -> splits.sumOf { this(pos + it) }
                else -> this(pos + Pos.DOWN)
            }
        }
        return solution(start)
    }



    val testInput = readInput("Day07_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 40L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}