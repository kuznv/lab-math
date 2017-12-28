package math

data class Point(val x: Double, val y: Double)

fun interpolate(points: List<Point>): (Double) -> Double {
    val dividers = points.mapIndexed { i, (xI) ->
        (points - points[i])
                .map { (xJ) -> xI - xJ }
                .reduce(Double::times)
    }

    return { x ->
        fun basisPolynomial(i: Int): Double =
                (points - points[i])
                        .map { (xJ) -> (x - xJ) }
                        .reduce(Double::times)
                        .div(dividers[i])

        points.mapIndexed { i, (_, yI) -> yI * basisPolynomial(i) }.sum()
    }
}