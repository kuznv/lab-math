package math.matrix

import util.mathContext
import util.sumByBigDecimal
import util.swapElements
import java.math.BigDecimal
import kotlin.math.min

fun Matrix.triangle() {
    val steps = min(rowsCount, columnsCount - 1)
    for (i in 0 until steps) {
        val primaryRowIndex = (i..lastIndex).maxBy { row -> this[row][i].abs() }!!

        swapElements(i, primaryRowIndex)

        val primaryElement = this[i][i]

        if (primaryElement == BigDecimal.ZERO) continue

        for (row in i + 1 until rowsCount) {
            val cell = this[row][i]
            val k = cell.divide(primaryElement, mathContext)

            for (column in i until columnsCount) {
                this[row][column] -= k * this[i][column]
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