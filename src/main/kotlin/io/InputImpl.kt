package io

import math.matrix.Matrix
import util.nextOrNull
import java.io.IOException
import java.math.BigDecimal
import java.text.ParseException
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

    return Matrix(List(rows) { rowIndex ->
        parse("Введите строку #${rowIndex + 1}\n") {
            val line = lines.nextOrNull() ?: throw IOException("Ожидалось $rows строк, введено ${rowIndex + 1}")
            val words = Scanner(line)

            List(columns) { columnIndex ->
                val word = words.nextOrNull() ?: throw ParseException("Ожидалось $columns значений, введено $columnIndex", 0)
                word.toBigDecimalOrNull() ?: throw ParseException("'$word' - не число", 0)
            }
        }
    })
}

interface MenuBuilder<in T> {
    operator fun String.invoke(onSelect: () -> T)
}

fun <T> UserInput.select(buildMenu: MenuBuilder<T>.() -> Unit): T {
    val menuEntries = mutableListOf<Pair<String, () -> T>>()

    object : MenuBuilder<T> {
        override fun String.invoke(onSelect: () -> T) {
            menuEntries += Pair(this, onSelect)
        }
    }.buildMenu()

    menuEntries.forEachIndexed { i, (message) ->
        println("  ${i + 1}. $message")
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