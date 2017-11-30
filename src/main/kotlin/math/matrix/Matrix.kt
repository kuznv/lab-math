package math.matrix

import util.*
import java.math.BigDecimal
import kotlin.math.min

typealias Cell = BigDecimal
typealias Row = MutableList<Cell>
typealias MatrixList = MutableList<Row>

open class Matrix(private val matrixList: MatrixList) : MatrixList by matrixList {
    constructor(rows: Int, columns: Int, init: (row: Int, column: Int) -> Cell) : this(
            MutableList(rows) { row ->
                MutableList(columns) { column ->
                    init(row, column)
                }
            }
    )

    val rowsCount inline get() = size

    val columnsCount = firstOrNull()?.size ?: 0

    open val smallestSide = min(rowsCount, columnsCount)

    val diagonal get() = List(smallestSide) { i -> this[i][i] }

    override fun toString() = joinToString(
            prefix = "Matrix(${rowsCount}x$columnsCount) [\n",
            separator = "\n",
            postfix = "\n]",
            transform = Row::makeString
    )

    override fun equals(other: Any?) = matrixList == other

    override fun hashCode() = matrixList.hashCode()
}