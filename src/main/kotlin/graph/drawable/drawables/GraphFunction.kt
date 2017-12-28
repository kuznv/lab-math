package graph.drawable.drawables

import graph.canvas.CanvasElement
import graph.canvas.CanvasView
import graph.drawable.Drawable

data class GraphFunction(
        val canvasElement: CanvasElement,
        val f: (Double) -> Double
) : Drawable {

    override fun draw(view: CanvasView) {
        val canvas = view.canvas
        canvas.xIndices
                .map(Int::toDouble)
                .forEach { canvasX ->
                    val graphX = view.xScaler.canvasToGraph(canvasX)
                    try {
                        val graphY = f(graphX)
                        if (!graphY.isFinite()) return@forEach
                        val canvasY = view.yScaler.graphToCanvas(graphY)
                        canvas[canvasX, canvasY] = canvasElement
                    } catch (e: ArithmeticException) {
                    }
                }
    }
}