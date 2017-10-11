import io.StreamInput
import io.randomMatrix
import io.readMatrix
import java.io.File

fun main(args: Array<String>) {
    val file = File("resources/test.txt")

    val n = 4

    StreamInput(file.inputStream()).use { input ->
//        val matrix = input.readMatrix(n, n)
        val matrix = randomMatrix(n, n)
        println(matrix)
    }

}