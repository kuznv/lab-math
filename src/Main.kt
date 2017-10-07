import math.matrix.triangle
import io.StreamInput
import io.randomMatrix
import java.io.File
import java.math.BigDecimal
import java.util.*
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val file = File("resources/test.txt")
    val input = StreamInput(file.inputStream())

    val n = 3
    val matrix = randomMatrix(n, n, Random(0))

    val time = measureTimeMillis {
        //        println(matrix.determinant())
        matrix.triangle()
        var a = BigDecimal.ONE
        matrix.forEachIndexed { i, row ->
            a *= row[i]
        }
        println(a)
    }
    println("$time ms.")
}