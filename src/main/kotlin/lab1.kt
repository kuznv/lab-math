import io.*
import math.matrix.ExtendedMatrix
import math.matrix.Matrix
import math.matrix.TriangularMatrix
import util.decimalFormat
import util.isZero
import java.io.File
import java.math.BigDecimal
import java.util.*

fun main(args: Array<String>) {
    val rows = UserInput.read("Введите количество строк в матрице") { it.toIntOrNull()?.takeIf { it > 0 } }
    val columns = rows + 1

    var matrix: Matrix = ExtendedMatrix(UserInput.select(
            "Из файла" to {
                val file = File("resources/test.txt")
                StreamInput(file.inputStream()).use { it.readMatrix(rows, columns) }
            },
            "Из консоли" to {
                println("Ввод матрицы ${rows}x$columns")
                UserInput.readMatrix(rows, columns)
            },
            "Случайные коэффициенты" to {
                randomMatrix(rows, columns, random = Random(0))
            }
    ))

    println("Исходная матрица: " + matrix)

    matrix = TriangularMatrix(matrix)
    println("Треугольная матрица: " + matrix)

    val determinant = matrix.determinant
    println("Определитель = " + decimalFormat.format(determinant))

    if (determinant.isZero) {
        println("Метод не применим")
        return
    }

    matrix = ExtendedMatrix(matrix)

    val solution = matrix.solve()
    println("Решение: " + solution.joinToString(transform = decimalFormat::format))

    val discrepancy = matrix.discrepancy(solution)
    println("Невязки: " + discrepancy.joinToString(transform = decimalFormat::format))
    println("Максимальное отклонение: " + discrepancy.maxBy(BigDecimal::abs).let(decimalFormat::format))
}