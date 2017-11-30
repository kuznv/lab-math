package io

import math.matrix.Matrix
import util.nextOrNull
import java.io.IOException
import java.math.BigDecimal
import java.util.*

fun randomMatrix(
        rows: Int,
        columns: Int,
        random: Random = Random(),
        maxValue: Int = 100
) = Matrix(rows, columns) { _, _ ->
    BigDecimal((0.5 - random.nextDouble()) * maxValue)
}

fun Input.readMatrix(rows: Int, columns: Int): Matrix {
    val lines = Scanner(inputStream).useDelimiter("\\R++")

    return Matrix(MutableList(rows) { rowIndex ->
        parse("Введите строку #${rowIndex + 1}\n") {
            val line = lines.nextOrNull() ?: throw IOException("Ожидалось $rows строк, введено ${rowIndex + 1}")
            val words = Scanner(line)

            MutableList(columns) { columnIndex ->
                val word = words.nextOrNull() ?: throw ParseException("Ожидалось $columns значений, введено $columnIndex")
                word.toBigDecimalOrNull() ?: throw ParseException("'$word' - не число")
            }
        }
    })
}

fun <T> UserInput.select(vararg menuEntries: Pair<String, () -> T>): T {
    menuEntries.forEachIndexed { i, (message) ->
        println("${i + 1}. $message")
    }

    while (true) {
        println("Выберите пункт меню (1-${menuEntries.size})")
        readLine()
                ?.toIntOrNull()
                ?.let { menuEntries.getOrNull(it - 1) }
                ?.let { (_, onSelect) -> return onSelect() }
    }
}

fun <T> UserInput.read(msg: String, parse: (String) -> T?): T {
    while (true) {
        println(msg)
        readLine()?.let(parse)?.let { return it }
    }
}

/*
fun Input.readMatrix(rows: Int, columns: Int): Matrix {
    val delimiterPattern = Pattern.compile("""$|[ \t]++""", Pattern.MULTILINE)
    val scanner = Scanner(inputStream).useDelimiter(delimiterPattern)
    val cellPattern = Pattern.compile("""[^\r\n]++""")
    val currentLine = StringBuilder()

    return Matrix(rows, columns) { rowIndex, columnIndex ->
        if (columnIndex == 0) {
            outputStream.println("Введите строку #${rowIndex + 1}")
        }

        var word: String
        var drawParams: DrawParams?

        do {
            if (scanner.hasNext(cellPattern))
                word = scanner.next(cellPattern)
            else
                do {
                    word = scanner.nextLine()
                    onException("В строке #${rowIndex + 1} ожидалось $columns элементов, найдено $columnIndex\n$currentLine")
                } while (!scanner.hasNext(cellPattern))


            drawParams = word.toBigDecimalOrNull()

            if (drawParams == null) {
                val errorOffset = currentLine.length + 2
                val errorLine = "$currentLine `$word`"

                scanner.skip(".*")

                onException(ParseException.getMessage(
                        "Ошибка при чтении ячейки [${rowIndex + 1}, ${columnIndex + 1}]",
                        errorLine,
                        errorOffset
                ))
            }
        } while (drawParams == null)

        val lastColumn = columns - 1
        if (columnIndex == lastColumn) {
            currentLine.setLength(0)
            scanner.skip(".*\r?\n?")
        } else {
            if (currentLine.isEmpty())
                currentLine.append(word)
            else
                currentLine.append(", $word")
        }

        drawParams
    }
}
*/