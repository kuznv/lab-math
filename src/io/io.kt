package io

import math.matrix.Cell
import math.matrix.Matrix
import math.matrix.Row
import util.nextOrNull
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.regex.Pattern

fun randomMatrix(rows: Int,
                 columns: Int,
                 random: Random = ThreadLocalRandom.current()
) = Matrix(rows, columns) { _, _ ->
    (random.nextDouble() * 1000).toBigDecimal()
}

fun Input.readMatrix(rows: Int, columns: Int): Matrix {
    val cellDelimiter = Pattern.compile("[ ,\t]++")
    val inputStreamReader = inputStream.reader()

    val lines = Scanner(inputStreamReader)
            .useDelimiter("\n")
            .asSequence()
            .filter { it.isNotBlank() }

    val scanner = Scanner(inputStreamReader).useDelimiter(cellDelimiter)
    val matrixList = parseMatrix(lines, rows, columns, scanner).toMutableList()

    require(matrixList.size == rows) { "$rows rows expected, ${matrixList.size} found" }

    return Matrix(matrixList)
}

private fun Input.parseMatrix(
        lines: Sequence<String>,
        rows: Int,
        columns: Int,
        inputScanner: Scanner
): Sequence<Row> = lines.take(rows).mapIndexed { row, line ->
    var scanner = Scanner(line)
    MutableList<Cell>(columns) { column ->
        var cell: Cell? = null
        do {
            val word = scanner.nextOrNull()
            if (word == null) {
                onException("Reading matrix row #${row + 1}: $columns elements expected, $column found")
                continue
            }

            cell = word.toBigDecimalOrNull()
            if (cell == null) {
                onException(ParseException.getMessage(
                        "Error reading matrix cell [${row + 1}, ${column + 1}]",
                        line,
                        errorOffset = line.indexOf(word)
                ))
                scanner = inputScanner
            }
        } while (cell == null)
        cell
    }
}