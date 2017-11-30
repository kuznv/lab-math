package math

data class Point(val x: Double, val y: Double)

fun interpolate(vararg points: Point): (Double) -> Double = { x ->
    fun basisPolynomial(i: Int, xI: Double): Double =
            points
                    .filterIndexed { j, _ -> j != i }
                    .map { (xJ) -> (x - xJ) / (xI - xJ) }
                    .reduce(Double::times)

    points.mapIndexed { i, (xI, yI) -> yI * basisPolynomial(i, xI) }.sum()
}