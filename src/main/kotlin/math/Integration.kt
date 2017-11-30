package math

import util.dropAfter
import kotlin.math.abs

private const val RUNGE_COEFF = 3

fun ((Double) -> Double).integrate(start: Double, end: Double, parts: Int): Double {
    if (start > end) return -integrate(end, start, parts)

    val step = (end - start) / parts
    return generateSequence(start) { x -> x + step }
            .take(parts + 1)
            .map(this)
            .zipWithNext { y1, y2 -> 0.5 * (y1 + y2) * step }
            .sum()
}

data class IntegrationStep(val square: Double, val nextStepSquare: Double) {
    val accuracy = abs(nextStepSquare - square) / RUNGE_COEFF

    operator fun component3() = accuracy
}

fun ((Double) -> Double).integrateWithAccuracy(
        start: Double,
        end: Double,
        accuracy: Double
): Sequence<IntegrationStep> =
        generateSequence(1) { n -> n * 2 }
                .map { n -> integrate(start, end, parts = n) }
                .zipWithNext(::IntegrationStep)
                .dropAfter { it.accuracy < accuracy }