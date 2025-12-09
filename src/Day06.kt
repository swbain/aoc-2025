sealed interface Instruction {
    sealed interface Action : Instruction {
        val index: Int
        data class Plus(override val index: Int) : Action
        data class Mult(override val index: Int) : Action
    }

    data object Empty : Instruction
}

fun main() {
    fun part1(input: List<String>): Long {
        val commands = input.last().split(" ").filter { it.isNotBlank() }
        val numberRows = input.dropLast(1).map { it.split(" ").filter { filter -> filter.isNotBlank() } }
        return (0..<commands.count()).sumOf { i ->
            val command = commands[i]
            val numbers = numberRows.map { it[i].toLong() }
            numbers.reduce { acc, value ->
                if (command == "*") acc * value
                else acc + value
            }
        }
    }

    data class ActionRange(val action: Instruction.Action, val range: IntRange)

    fun part2(input: List<String>): Long {
        val commandLine = input.last()
        val instructions = commandLine.mapIndexed { index, char ->
            when (char) {
                '*' -> Instruction.Action.Mult(index)
                '+' -> Instruction.Action.Plus(index)
                else -> Instruction.Empty
            }
        }

        val finalActionIndex = instructions.indexOfLast { it is Instruction.Action }
        val actionRanges = instructions.mapIndexed { index, instruction ->
            when (instruction) {
                is Instruction.Action -> {
                    if (index == finalActionIndex) {
                        ActionRange(
                            action = instruction,
                            range = IntRange(index, input.maxOf { it.count() } - 1)
                        )
                    } else {
                        val nextIndex = instructions.drop(index + 1).indexOfFirst { it is Instruction.Action} + index
                        ActionRange(
                            action = instruction,
                            range = IntRange(index, nextIndex - 1)
                        )
                    }
                }
                Instruction.Empty -> null
            }
        }.filterNotNull()

        val numberLines = input.dropLast(1)
        return actionRanges.sumOf { actionRange ->
            val nums = actionRange.range.map { i ->
                val nums = numberLines.map { it.getOrNull(i)?.toString() ?: " " }
                nums.joinToString(separator = "").trim().toLong()
            }

            nums.reduce { acc, num ->
                when (actionRange.action) {
                    is Instruction.Action.Mult -> acc * num
                    is Instruction.Action.Plus -> acc + num
                }
            }

        }
    }

    val testInput = readInputNoTrim("Day06_test")

    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)


    val input = readInputNoTrim("Day06")
    part1(input).println()
    part2(input).println()
}