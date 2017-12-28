package util

import math.matrix.Cell
import math.matrix.Row
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.coroutines.experimental.buildSequence

const val FRACTION_DIGITS = 3

val decimalFormat = DecimalFormat().apply {
    minimumFractionDigits = FRACTION_DIGITS
    maximumFractionDigits = FRACTION_DIGITS
    isGroupingUsed = false
    positivePrefix = " "
}

val mathContext = MathContext(FRACTION_DIGITS, RoundingMode.HALF_UP)

fun <T> Iterator<T>.nextOrNull(): T? = if (hasNext()) next() else null

inline fun <T> Iterable<T>.sumBy(select: (T) -> BigDecimal): BigDecimal =
        fold(BigDecimal.ZERO) { sum, element -> sum + select(element) }

inline fun <T> Sequence<T>.sumBy(select: (T) -> BigDecimal): BigDecimal =
        fold(BigDecimal.ZERO) { sum, element -> sum + select(element) }

fun <T> Sequence<T>.dropAfter(predicate: (T) -> Boolean): Sequence<T> = buildSequence {
    for (item in this@dropAfter) {
        yield(item)
        if (predicate(item))
            break
    }
}

fun Iterable<BigDecimal>.sum() = fold(BigDecimal.ZERO, BigDecimal::plus)
fun Sequence<BigDecimal>.sum() = fold(BigDecimal.ZERO, BigDecimal::plus)

fun <E> MutableList<E>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

fun IntRange.toDoubleRange(): ClosedFloatingPointRange<Double> = start.toDouble()..endInclusive.toDouble()
fun ClosedFloatingPointRange<Double>.toIntRange() = start.toInt()..endInclusive.toInt()

val BigDecimal.isZero get() = signum() == 0

fun Double.format() = decimalFormat.format(this)!!
fun Cell.format() = decimalFormat.format(this)!!
fun Row.format() = joinToString(separator = "\t", transform = Cell::format)

fun CharSequence?.orEmpty() = this ?: ""

typealias DoubleRange = ClosedFloatingPointRange<Double>

fun <T> Sequence<T>.loop(): Sequence<T> = buildSequence {
    while (true) {
        val iterator = iterator()
        if (!iterator.hasNext()) break
        yieldAll(iterator)
    }
}