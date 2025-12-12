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
        return coords.allCombinations().maxOf { (a, b) -> area(a, b) }
    }

    fun part2(input: List<String>): Long {
        val coords = input.map {
            val split = it.split(",")
            Pos(split.first().toInt(), split.last().toInt())
        }

        val edges = (coords + coords.first()).zipWithNext()

        return coords.allCombinations().filter { (a, b) ->
            val topLeft = Pos(minOf(a.x, b.x), minOf(a.y, b.y))
            val bottomRight = Pos(maxOf(a.x, b.x), maxOf(a.y, b.y))
            edges.none { edge ->
                val edgeTopLeft = Pos(minOf(edge.first.x, edge.second.x), minOf(edge.first.y, edge.second.y))
                val edgeBottomRight = Pos(maxOf(edge.first.x, edge.second.x), maxOf(edge.first.y, edge.second.y))

                // if all of these are true, the box we are checking overlaps with an edge
                val cond1 = topLeft.x < edgeBottomRight.x
                val cond2 = bottomRight.x > edgeTopLeft.x
                val cond3 = topLeft.y < edgeBottomRight.y
                val cond4 = bottomRight.y > edgeTopLeft.y

                cond1 && cond2 && cond3 && cond4
            }
        }.maxOf { (a, b) -> area(a, b) }
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 50L)
    check(part2(testInput) == 24L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}