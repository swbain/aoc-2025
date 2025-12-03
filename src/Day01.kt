fun main() {
    fun part1(input: List<String>): Int {
        return input.asSequence().map {
            it.toCommand()
        }.runningFold(50) { acc, command ->
            val nextNotch = acc + (command.count % 100).toInt() * if (command.direction == Direction.LEFT) -1 else 1
            when {
                nextNotch < 0 -> 100 + nextNotch
                nextNotch > 99 -> nextNotch - 100
                else -> nextNotch
            }
        }.count { it == 0 }
    }

    fun part2(input: List<String>): Int {
        var zeroClicks = 0
        var notch = 50
        input.map { it.toCommand() }.forEach {
            zeroClicks += (it.count / 100).toInt()
            val next = notch
            notch += (it.count % 100).toInt() * if (it.direction == Direction.LEFT) -1 else 1
            when {
                notch > 99 -> {
                    if (notch != 100) zeroClicks++
                    notch -= 100
                }
                notch < 0 -> {
                    if (next != 0) zeroClicks++
                    notch += 100
                }
            }

            if (notch == 0) {
                zeroClicks++
            }
        }
        return zeroClicks
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

enum class Direction {
    LEFT,
    RIGHT
}

data class Command(val direction: Direction, val count: Long)

fun String.toCommand(): Command = Command(
    direction = when (first()) {
        'L' -> Direction.LEFT
        'R' -> Direction.RIGHT
        else -> throw IllegalArgumentException("must be L or R")
    },
    count = this.drop(1).toLong()
)
