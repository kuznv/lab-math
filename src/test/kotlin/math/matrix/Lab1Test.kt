package math.matrix

import io.StreamInput
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
        val triangularMatrix = matrix as TriangularMatrix
        Assertions.assertEquals(triangularMatrix.determinant.toDouble(), -54.0)
    }

    @Test
    fun toTriangularMatrix() {
        val expectedMatrix = Matrix(
                mutableListOf(
                        mutableListOf(BigDecimal(4), BigDecimal(1), BigDecimal(3), BigDecimal(3)),
                        mutableListOf(BigDecimal(0), BigDecimal(2.25), BigDecimal(-3.25), BigDecimal(-0.25)),
                        mutableListOf(BigDecimal(0), BigDecimal(0), BigDecimal(-6), BigDecimal(3))
                )
        )
        matrix = TriangularMatrix(matrix)
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
            matrix = StreamInput(file.inputStream()).use { it.readMatrix(rows, columns) }.let(::ExtendedMatrix)

            val expectedMatrix = Matrix(
                    mutableListOf(
                            mutableListOf(BigDecimal(3), BigDecimal(3), BigDecimal(-1), BigDecimal(2)),
                            mutableListOf(BigDecimal(4), BigDecimal(1), BigDecimal(3), BigDecimal(3)),
                            mutableListOf(BigDecimal(1), BigDecimal(-2), BigDecimal(-2), BigDecimal(4))
                    )
            )

            Assertions.assertEquals(expectedMatrix.toString(), matrix.toString())
        }
    }
}