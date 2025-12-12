import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun main() {
    data class Connection(val coords: Set<Pos3d>, val distance: Int)

    data class Circuit(val coords: Set<Pos3d>) {
        operator fun plus(circuit: Circuit): Circuit {
            return Circuit(coords = coords + circuit.coords)
        }
    }

    fun solution(input: List<String>, runCount: Int): Int {
        val coords = input.map {
            val split = it.split(",")
            Pos3d(x = split[0].toInt(), y = split[1].toInt(), z = split[2].toInt())
        }

        fun distance(first: Pos3d, second: Pos3d): Int {
            return sqrt(
                (first.x - second.x).toDouble().pow(2) +
                        (first.y - second.y).toDouble().pow(2) +
                        (first.z - second.z).toDouble().pow(2)
            ).roundToInt()
        }

        val connections = coords.flatMap { x ->
            coords.flatMap { y ->
                if (x == y) emptyList()
                else listOf(
                    Connection(
                        coords = setOf(x, y),
                        distance = distance(x, y)
                    )
                )
            }
        }.toSet().sortedBy { it.distance }

        return connections.take(runCount).fold(coords.map { Circuit(setOf(it)) }) { circuits, connection ->
            val a = connection.coords.first()
            val b = connection.coords.last()

            if (circuits.any { it.coords.contains(a) && it.coords.contains(b) }) return@fold circuits

            val left = circuits.find { it.coords.contains(a) } ?: Circuit(emptySet())
            val right = circuits.find { it.coords.contains(b) } ?: Circuit(emptySet())

            circuits.toMutableList().apply {
                remove(left)
                remove(right)
                add(left + right)
            }.also {
                if (it.count() == 1) return@solution a.x * b.x
            }
        }.sortedByDescending { it.coords.size }.take(3).fold(1) { acc, circuit ->
            acc * circuit.coords.count()
        }
    }

    fun part1(input: List<String>): Int {
        return solution(input, 1000)
    }

    fun part2(input: List<String>): Int {
        return solution(input, Int.MAX_VALUE)
    }

    val testInput = readInput("Day08_test")
//    check(part1(testInput) == 40)
    check(part2(testInput) == 25272)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}