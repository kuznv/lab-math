package math.matrix

import io.Input
import io.readMatrix
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File
import java.math.BigDecimal
import kotlin.test.assertEquals

internal class Lab1Test {
    @Test
    fun determinant() {
        Assertions.assertEquals(matrix.determinant.toDouble(), -54.0)
    }

    @Test
    fun toTriangularMatrix() {
        val expectedMatrix = Matrix(
                listOf(
                        listOf(BigDecimal(4), BigDecimal(1), BigDecimal(3), BigDecimal(3)),
                        listOf(BigDecimal(0), BigDecimal(2.25), BigDecimal(-3.25), BigDecimal(-0.25)),
                        listOf(BigDecimal(0), BigDecimal(0), BigDecimal(-6), BigDecimal(3))
                )
        )
        matrix = matrix.toTriangularMatrix()
        assertEquals(expectedMatrix.toString(), matrix.toString())
    }

    companion object {
        lateinit var matrix: Matrix

        @BeforeAll
        @Test
        @JvmStatic
        fun readMatrixFromFile() {
            val rows = 3
            val columns = rows + 1
            val file = File("resources/test.txt")
            matrix = Input(file.inputStream()).use { it.readMatrix(rows, columns) }.let(::ExtendedMatrix)

            val expectedMatrix = Matrix(
                    listOf(
                            listOf(BigDecimal(3), BigDecimal(3), BigDecimal(-1), BigDecimal(2)),
                            listOf(BigDecimal(4), BigDecimal(1), BigDecimal(3), BigDecimal(3)),
                            listOf(BigDecimal(1), BigDecimal(-2), BigDecimal(-2), BigDecimal(4))
                    )
            )

            Assertions.assertEquals(expectedMatrix.toString(), matrix.toString())
        }
    }
}