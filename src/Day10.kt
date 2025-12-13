import java.util.BitSet

fun main() {

    class Machine(
        val target: BitSet,
        val buttons: List<BitSet>,
        val joltages: List<Int>
    ) {
        fun minButtonPresses(): Int {
            val queue = ArrayDeque<Pair<BitSet, Int>>()
            val visited = mutableSetOf<BitSet>()
            val start = BitSet(target.size())
            queue.add(start to 0)
            while (queue.isNotEmpty()) {
                val (bitSet, buttonPresses) = queue.removeFirst()
                if (bitSet == target) return buttonPresses

                buttons.forEach {
                    val nextSet = bitSet.clone() as BitSet
                    nextSet.xor(it)
                    if (nextSet !in visited) {
                        visited.add(nextSet)
                        queue.addLast(nextSet to buttonPresses + 1)
                    }
                }
            }
            return 0
        }
    }

    fun String.toMachine(): Machine {
        val split = split(" ")
        val targetBools = split.first().drop(1).dropLast(1).map { it == '#' }
        val buttonStrings = split.drop(1).dropLast(1)

        val target = BitSet(targetBools.count())
        targetBools.forEachIndexed { index, b -> if (b) target.set(index) }

        val buttons = buttonStrings.map { buttonString ->
            val nums = buttonString.drop(1).dropLast(1).split(",").map { it.toInt() }
            BitSet(targetBools.size).also { bs ->
                nums.forEach { bs.set(it) }
            }
        }

        val joltages = split.last().drop(1).dropLast(1).split(",").map { it.toInt() }

        return Machine(target, buttons, joltages)
    }

    fun part1(input: List<String>): Int {
        return input.map { it.toMachine() }.sumOf { it.minButtonPresses() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 7)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}