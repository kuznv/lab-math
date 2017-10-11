package io

import math.matrix.Cell
import math.matrix.Matrix
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.regex.Pattern

fun randomMatrix(
        rows: Int,
        columns: Int,
        random: Random = ThreadLocalRandom.current()
) = Matrix(rows, columns) { _, _ ->
    BigDecimal(random.nextDouble() * 100000)
}

fun Input.readMatrix(rows: Int, columns: Int): Matrix {
//    val cellDelimiter = Pattern.compile("[ ,\t\n]++")
    val cellPattern = Pattern.compile("([^ ,\t\n]+)[ ,\t]*+")
    val scanner = Scanner(inputStream).useDelimiter("")

    val currentLine = StringBuilder()

    return Matrix(rows, columns) { rowIndex, columnIndex ->
        var cell: Cell?
        var word: String

        do {
            while (!scanner.hasNext(cellPattern)) {
                onException("Reading matrix row #${rowIndex + 1}: $columns elements expected, $columnIndex found\n$currentLine")
            }

            scanner.next(cellPattern)
            word = scanner.match().group(1)
            cell = word.toBigDecimalOrNull()

            if (cell == null) {
                val errorOffset = currentLine.length + 1
                val errorLine = "$currentLine $word"

                onException(ParseException.getMessage(
                        "Error reading matrix cell [${rowIndex + 1}, ${columnIndex + 1}]",
                        errorLine,
                        errorOffset
                ))
            }
        } while (cell == null)

        if (columnIndex == columns - 1) {
            currentLine.setLength(0)

            if (rowIndex != rows - 1) {
                scanner.nextLine()
            }
        } else {
            currentLine.append(word).append(' ')
        }

        cell
    }
}