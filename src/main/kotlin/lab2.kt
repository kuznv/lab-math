import io.UserInput
import io.read
import io.select
import math.integrate
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val f: (Double) -> Double = UserInput.select {
        "1/x" {
            { x -> 1 / x }
        }
        "sin(x)" {
            { x -> sin(x) }
        }
        "sqrt(x)" {
            { x -> sqrt(x) }
        }
        "x^3 + 5x^2 - 9x" {
            { x -> x.pow(3) + 5 * x.pow(2) - 9 * x }
        }
    }

    val start = UserInput.read("Введите нижний предел интегрирования", parse = String::toDoubleOrNull)
    val end = UserInput.read("Введите верхний предел интегрирования", parse = String::toDoubleOrNull)
    val accuracy = UserInput.read("Введите точность") { it.toDoubleOrNull()?.takeIf { it >= 0.0 } }

    try {
        f
                .asArithmeticFunction()
                .integrate(start, end, accuracy)
                .mapIndexed { i, (square, _, accuracy) ->
                    "${i + 1}) S=$square (${1.shl(i)} частей), точность = $accuracy"
                }
                .forEach(::println)
    } catch (e: ArithmeticException) {
        System.err.println(e.message)
    }
}

private fun ((Double) -> Double).asArithmeticFunction(): (Double) -> Double = { x ->
    fun makeException() = ArithmeticException("x = $x не входит в область значений функции")

    try {
        this(x)
    } catch (e: ArithmeticException) {
        throw makeException().apply { initCause(e) }
    }.takeIf(Double::isFinite) ?: throw makeException()
}