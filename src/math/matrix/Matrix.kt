package math.matrix

import util.decimalFormat
import java.math.BigDecimal

typealias Cell = BigDecimal
typealias Row = MutableList<Cell>
typealias MatrixList = MutableList<Row>

class Matrix private constructor(list: MatrixList, needToCheckList: Boolean) : MatrixList by list, Cloneable {

    init {
        if (needToCheckList) list.checkRowsLengthsAreEqual()
    }

    constructor(list: MatrixList) : this(list, needToCheckList = true)

    constructor(rows: Int, columns: Int, init: (row: Int, column: Int) -> Cell) : this(
            MutableList(rows) { row ->
                MutableList(columns) { column ->
                    init(row, column)
                }
            },
            needToCheckList = false
    )

    inline val rowsCount get() = size

    inline val columnsCount get() = firstOrNull()?.size ?: 0

    public override fun clone() = Matrix(mapTo(ArrayList(rowsCount)) { row -> ArrayList(row) }, needToCheckList = false)

    override fun toString() = joinToString(
            prefix = "Matrix(${rowsCount}x$columnsCount) [\n",
            separator = "\n",
            postfix = "\n]",
            transform = Row::toString2
    )
}

private fun MatrixList.checkRowsLengthsAreEqual() {
    if (size < 2) return

    reduceIndexed { i, row, nextRow ->
        require(row.size == nextRow.size) {
            "Matrix row lengths are not equal:\n" +
                    "matrix[$i]: ${row.toString2()} - ${row.size} elements\n" +
                    "matrix[${i + 1}]: ${nextRow.toString2()} - ${nextRow.size} elements"
        }
        nextRow
    }
}

fun Row.toString2() = joinToString(separator = "\t", transform = Cell::toString2)

fun Cell.toString2(): String = decimalFormat.format(this)