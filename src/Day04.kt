import kotlin.system.measureTimeMillis

fun main() {

data class Point(val x: Int, val y: Int, val isPaper: Boolean)

fun removableItems(input: List<String>): List<Point> {
    return input.mapIndexed { y, line ->
        line.mapIndexed { x, char ->
            Point(x, y, char == '@')
        }.filter {
            if (it.isPaper) {
                // check neighbors
                val validX = IntRange(
                    start = maxOf(0, it.x - 1),
                    endInclusive = minOf(line.length - 1, it.x + 1)
                )
                val validY = IntRange(
                    start = maxOf(0, it.y - 1),
                    endInclusive = minOf(it.y + 1, input.size - 1)
                )
                return@filter validX.sumOf { neighborX ->
                    validY.sumOf { neighborY ->
                        if (!(neighborY == it.y && neighborX == it.x)) {
                            val neighborChar = input[neighborY][neighborX]
                            if (neighborChar == '@') return@sumOf 1
                        }
                        0
                    }
                } < 4
            }
            false
        }
    }.flatten()
}

fun part1(input: List<String>): Int {
    return removableItems(input).size
}

fun List<String>.removeItems(points: List<Point>): List<String> {
    val newList = this.toMutableList()
    points.forEach { point ->
        val charArray = newList[point.y].toCharArray()
        charArray[point.x] = '.'
        newList[point.y] = charArray.concatToString()
    }
    return newList
}

fun part2(input: List<String>): Int {
    var removableItems = removableItems(input)
    var itemCount = removableItems.size
    var finalItemCount = itemCount
    var items = input
    while (itemCount > 0) {
        val newList = items.removeItems(removableItems)
        removableItems = removableItems(newList)
        itemCount = removableItems.size
        finalItemCount += itemCount
        items = newList
    }
    return finalItemCount
}

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    val part1 = measureTimeMillis {
        part1(input).println()
    }
    val part2 = measureTimeMillis {
        part2(input).println()
    }
    "part 1 took $part1 ms".println()
    "part 2 took $part2 ms".println()
}