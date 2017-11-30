package util

import math.matrix.Cell
import math.matrix.Row
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.coroutines.experimental.buildSequence

const val FRACTION_DIGITS = 15

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

fun <E> MutableList<E>.swapElements(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}

val BigDecimal.isZero get() = signum() == 0

fun Row.makeString() = joinToString(separator = "\t", transform = Cell::makeString)
fun Cell.makeString() = decimalFormat.format(this)!!

fun CharSequence?.orEmpty() = this ?: ""

inline fun <A, B, C> ((A) -> B).compose(crossinline f: (B) -> C): (A) -> C = { a -> f(this(a)) }

fun <T> T?.ifNull(action: () -> Unit) = apply { if (this == null) action() }