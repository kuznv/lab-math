package math.matrix

import util.mathContext
import util.sumByBigDecimal
import util.swapElements
import java.math.BigDecimal
import kotlin.math.min

fun Matrix.toTriangular() {
    val steps = min(rowsCount, columnsCount - 1)
    for (i in 0 until steps) {
        val primaryRowIndex = (i..lastIndex).maxBy { rowIndex -> this[rowIndex][i].abs() }!!

        swapElements(i, primaryRowIndex)

        val primaryElement = this[i][i]

        if (primaryElement == BigDecimal.ZERO) continue

        for (rowIndex in i + 1 until rowsCount) {
            val cell = this[rowIndex][i]
            val k = cell.divide(primaryElement, mathContext)

            for (columnIndex in i until columnsCount) {
                this[rowIndex][columnIndex] -= k * this[i][columnIndex]
            }
        }
    }
}

fun Matrix.solve(): Row {
    val xs = MutableList<Cell>(rowsCount) { Cell.ZERO }
    val lastColumn = columnsCount - 1

    for (i in indices.reversed()) {
        val row = this[i]
        val cell = row[i]
        val b = row[lastColumn]

        val sum = (i + 1 until lastColumn).sumByBigDecimal { j -> row[j] * xs[j] }

        xs[i] = (b - sum).divide(cell, mathContext)
    }

    return xs
}