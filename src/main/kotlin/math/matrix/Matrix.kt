package math.matrix

import util.format
import java.math.BigDecimal
import kotlin.math.min

typealias Cell = BigDecimal
typealias Row = List<Cell>
typealias MatrixList = List<Row>

class Matrix(matrixList: MatrixList) : MatrixList by matrixList {
    constructor(rows: Int, columns: Int, init: (row: Int, column: Int) -> Cell) : this(
            List(rows) { row ->
                List(columns) { column ->
                    init(row, column)
                }
            }
    )

    val rowsCount inline get() = size

    val columnsCount = firstOrNull()?.size ?: 0

    val diagonal get() = List(min(rowsCount, columnsCount)) { i -> this[i][i] }

    override fun toString() = joinToString(
            prefix = "Matrix(${rowsCount}x$columnsCount) [\n",
            separator = "\n",
            postfix = "\n]",
            transform = Row::format
    )
}