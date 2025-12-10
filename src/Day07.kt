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

    fun solution(grid: Grid, pos: Pos, cache: HashMap<Pos, Long>): Long {
        return when {
            !grid.contains(pos) -> 1L
            grid[pos].value == '^' -> splits.sumOf { cache.getOrPut(pos + it) { solution(grid, pos + it, cache) } }
            else -> cache.getOrPut(pos + Pos.DOWN) { solution(grid, pos + Pos.DOWN, cache) }
        }
    }

    fun part2(input: List<String>): Long {
        val grid = Grid(input)
        val start = grid.data.flatten().first { it.value == 'S' }.pos
        return solution(grid, start, hashMapOf())
    }



    val testInput = readInput("Day07_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 40L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}