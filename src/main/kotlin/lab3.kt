import graph.Graph
import graph.canvas.Canvas
import graph.canvas.CanvasElement
import graph.canvas.CanvasView
import graph.drawable.TextStyle
import graph.drawable.Drawable
import graph.drawable.drawables.GraphAxis
import graph.drawable.drawables.GraphFunction
import graph.drawable.drawables.GraphLabel
import graph.drawable.drawables.GraphPoint
import io.UserInput
import io.select
import math.Point
import math.interpolate
import util.AnsiCode.BackgroundColor.BACKGROUND_RED
import util.AnsiCode.Color.*
import util.AnsiCode.Intensity.HIGH_INTENSITY
import util.decimalFormat
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun main(args: Array<String>) {
    while (true) {
        val (f0, points) = selectFunctionAndPoints()
        val f = interpolate(points)

        val graphPoints = points.map { (x, y) ->
            GraphPoint(x, y, CanvasElement("*", TextStyle(BLACK, BACKGROUND_RED)))
        }
        val graphFunctions = listOf(
                "Интерполированная функция" to
                        GraphFunction(CanvasElement("+", TextStyle(GREEN)), f),
                "Исходная функция" to
                        GraphFunction(CanvasElement("˖", TextStyle(RED, intensity = HIGH_INTENSITY)), f0)
        )
        val axis = GraphAxis(TextStyle(BLUE), decimalFormat)
        val drawables = graphFunctions.map { it.second } + graphPoints + axis

        val graph = getGraph(drawables)
        showGraphMenu(graph, graphFunctions, graphPoints)
    }
}

private fun getGraph(drawables: List<Drawable>): Graph {
    val canvas = Canvas(height = 36, width = 185)
    val y1 = -2.0
    val y2 = 2.0
    val x1 = y1 * canvas.xyScale
    val x2 = y2 * canvas.xyScale

    val view = CanvasView(canvas, x1..x2, y1..y2)
    return Graph(drawables, view)
}

private fun selectFunctionAndPoints(): Pair<(Double) -> Double, List<Point>> = UserInput.select {
    "cos, 4 точки на интервале 0 по 2Пи" {
        ::cos to listOf(
                PI / 8,
                PI / 2,
                PI * 1.5,
                PI * 2
        ).map { x -> Point(x, cos(x)) }
    }

    val points2 = listOf(
            0.0,
            PI / 8,
            PI / 7,
            PI / 3,
            PI / 2,
            PI * 1.2,
            PI * 1.5,
            PI * 1.8,
            PI * 2
    ).map { x -> Point(x, sin(x)) }

    "sin, 9 точек на интервале 0 по 2Пи" {
        ::sin to points2
    }
    "sin, точки с предыдущего примера с 1 измененной точкой" {
        ::sin to points2.toMutableList().also {
            it[3] = it[3].copy(y = 1.1)
        }
    }
    "sin, 9 точек на интервале 0 по 50Пи" {
        val max = PI * 50
        val nPoints = 9.5
        ::sin to generateSequence(0.0) { it + max / nPoints }
                .takeWhile { it < max }
                .map { x -> Point(x, sin(x)) }
                .toList()
    }
}

private fun showGraphMenu(
        graph: Graph,
        graphFunctions: List<Pair<String, GraphFunction>>,
        graphPoints: List<GraphPoint>
) {
    graph.addLabels(graphFunctions, graphPoints)
    graph.paint()

    do {
        val view = graph.view
        val height = view.canvas.height
        val width = view.canvas.width
        graph.view = view.copy(canvas = Canvas(height, width))

        println("""
        |Перемещение по графику: ${RED("W A S D")}, масштаб: ${RED("+/-")}
        |Перейти к точке: ${RED("<x> <y>")}
        |Выход: ${RED("q")}"""
                .trimMargin())

        val xRange = graph.view.xRange
        val yRange = graph.view.yRange

        val dx = xRange.endInclusive - xRange.start
        val dy = yRange.endInclusive - yRange.start

        var x = xRange.start + dx / 2
        var y = yRange.start + dy / 2

        var scale = 1.0

        val input = readLine() ?: return
        val tokens = input.split(' ')

        var xRead = false
        var yRead = false
        tokens.forEach { s ->
            val number = s.toDoubleOrNull()
            if (number != null) {
                if (!xRead) {
                    x = number; xRead = true
                } else if (!yRead) {
                    y = number; yRead = true
                }
            } else {
                val xStep = dx / 10.0
                val yStep = dy / 10.0

                s.forEach { char ->
                    when (char.toLowerCase()) {
                        'w' -> y += yStep
                        'a' -> x -= xStep
                        's' -> y -= yStep
                        'd' -> x += xStep
                        '+' -> scale *= 0.8
                        '-' -> scale *= 1.2
                        'q' -> return
                    }
                }
            }

        }

        val rx = dx * scale / 2
        val ry = dy * scale / 2

        graph.view = graph.view.copy(
                xRange = x - rx..x + rx,
                yRange = y - ry..y + ry
        )
        graph.paint()

        if (xRead || yRead) {
            graphFunctions.forEach { (name, function) ->
                val drawParams = function.canvasElement.textStyle
                println(drawParams.apply(name) + " ($x) = ${function.f(x)}")
            }
        }
    } while (true)
}

private fun Graph.addLabels(
        graphFunctions: List<Pair<String, GraphFunction>>,
        graphPoints: List<GraphPoint>
) {
    val x = 10
    var y = 2
    drawables += graphFunctions.map { (name, function) ->
        val symbols = function.canvasElement.symbols
        val functionSymbols = List(5) { symbols[it % symbols.length] }.joinToString(prefix = " ", separator = "")
        val drawParams = function.canvasElement.textStyle
        GraphLabel(x = x, y = y++, element = CanvasElement(name + functionSymbols, drawParams))
    }
    val pointCanvasElement = graphPoints.first().canvasElement
    drawables += GraphLabel(x, y++, CanvasElement(
            "Точки интерполяции: ${pointCanvasElement.symbols} ",
            pointCanvasElement.textStyle
    ))
}