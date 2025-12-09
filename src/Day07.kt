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

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 40)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}