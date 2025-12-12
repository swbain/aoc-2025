import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

fun readInputNoTrim(name: String) = Path("src/$name.txt").readText().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

data class Pos(val x: Int, val y: Int) {
    operator fun plus(pos: Pos) = Pos(x = x + pos.x, y = y + pos.y)

    companion object {
        val UP = Pos(0, -1)
        val DOWN = Pos(0, 1)
        val LEFT = Pos(-1, 0)
        val RIGHT = Pos(1, 0)
    }
}

data class Posl(val x: Long, val y: Long) {
    operator fun plus(pos: Posl) = Posl(x = x + pos.x, y = y + pos.y)

    companion object {
        val UP = Posl(0, -1)
        val DOWN = Posl(0, 1)
        val LEFT = Posl(-1, 0)
        val RIGHT = Posl(1, 0)
    }
}

data class GridItem(val pos: Pos, val value: Char)

class Grid(input: List<String>) {
    val data = input.mapIndexed { y, line ->
        line.mapIndexed { x, char ->
            GridItem(
                pos = Pos(x, y),
                value = char
            )
        }
    }

    val width = data.first().count()
    val height = data.count()

    fun get(x: Int, y: Int) = data[y][x]

    operator fun get(pos: Pos) = get(pos.x, pos.y)

    fun contains(x: Int, y: Int) = (x in 0 until width) && (y in 0 until height)

    operator fun contains(pos: Pos) = contains(pos.x, pos.y)
}

data class Pos3d(val x: Int, val y: Int, val z: Int)

