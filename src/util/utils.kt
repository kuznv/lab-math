package util

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat

val decimalFormat = DecimalFormat().apply {
    maximumFractionDigits = 2
    minimumFractionDigits = 2
//    minimumIntegerDigits = 4
    isGroupingUsed = false
    positivePrefix = " "
}

val mathContext = MathContext(100, RoundingMode.HALF_UP)

inline fun <T> Iterable<T>.sumByBigDecimal(select: (T) -> BigDecimal): BigDecimal =
        fold(BigDecimal.ZERO) { sum, next -> sum + select(next) }

val Int.isOdd: Boolean get() = rem(2) == 0

fun <E> MutableList<E>.swapElements(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}