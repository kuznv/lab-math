import io.*
import math.matrix.*
import util.isZero
import util.format
import java.io.File
import java.math.BigDecimal
import java.util.*

fun main(args: Array<String>) {
    val rowsCount = UserInput.read("Введите количество строк в матрице") { it.toIntOrNull()?.takeIf { it > 0 } }
    val columnsCount = rowsCount + 1

    val matrix: Matrix = UserInput.select {
        "Из файла" {
            val file = File("resources/test.txt")
            Input(file.inputStream()).use { it.readMatrix(rowsCount, columnsCount) }
        }
        "Из консоли" {
            println("Ввод матрицы ${rowsCount}x$columnsCount")
            UserInput.readMatrix(rowsCount, columnsCount)
        }
        "Случайные коэффициенты" {
            randomMatrix(rowsCount, columnsCount, random = Random(0))
        }
    }

    println("Исходная матрица: $matrix")

    val triangularMatrix = matrix.toTriangularMatrix()
    println("Треугольная матрица: $triangularMatrix")

    val determinant = triangularMatrix.determinant
    println("Определитель = ${determinant.format()}")

    if (determinant.isZero) {
        println("Метод не применим")
        return
    }

    val solution = triangularMatrix.solve()
    println("Решение: ${solution.format()}")

    val discrepancy = triangularMatrix.getDiscrepancy(solution)
    println("Невязки: ${discrepancy.format()}")
    println("Максимальное погрешность: ${discrepancy.maxBy(BigDecimal::abs)?.format()}")
}