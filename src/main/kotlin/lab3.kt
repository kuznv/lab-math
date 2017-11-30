import math.Point
import math.interpolate
import util.decimalFormat
import kotlin.math.PI

fun main(args: Array<String>) {
    val f0 = Math::sin

    //@formatter:off
    val f = interpolate(
            Point(0.0   ,   0.0),
            Point(PI / 4,   0.707),
            Point(PI / 2,   1.0),
            Point(PI    ,   0.0)
    )
    //@formatter:on

    generateSequence(0.0) { it + 0.1 }
            .takeWhile { it <= PI }
            .forEach { x ->
                val y0 = f0(x)
                val y = f(x)
                val dy = y - y0
                println("x=${format(x)}" +
                        "\ty0=${format(y0)}" +
                        "\ty=${format(y)}" +
                        "\tdy=${format(dy)}"
                )
            }

/*
    Graph().apply {
        drawables += util.graph.Function(DrawParams(symbol = "·", color = Color.BLUE), f0)
        drawables += util.graph.Function(DrawParams(symbol = "*", color = Color.RED), f)
        paint(xMin = 0.0, xMax = PI)
    }
*/
}

fun format(x: Double) = decimalFormat.format(x)!!