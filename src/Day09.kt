import kotlin.math.absoluteValue

fun main() {

    fun area(a: Pos, b: Pos): Long {
        val width = (a.x - b.x).absoluteValue + 1
        val height = (a.y - b.y).absoluteValue + 1

        return width.toLong() * height.toLong()
    }

    fun part1(input: List<String>): Long {
        val coords = input.map {
            val split = it.split(",")
            Pos(split.first().toInt(), split.last().toInt())
        }
        return coords.flatMap { a ->
            coords.flatMap { b ->
                if (a == b) emptyList()
                else listOf(area(a, b))
            }
        }.max()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 50L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}