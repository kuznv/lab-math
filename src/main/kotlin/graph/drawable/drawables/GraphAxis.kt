package graph.drawable.drawables

import graph.canvas.CanvasElement
import graph.canvas.CanvasView
import graph.drawable.TextStyle
import graph.drawable.Drawable
import util.AnsiCode.Color.GREEN
import util.AnsiCode.Intensity.HIGH_INTENSITY
import java.text.DecimalFormat

class GraphAxis(
        private val textStyle: TextStyle,
        private val decimalFormat: DecimalFormat,
        private val top: Boolean = true,
        private val bottom: Boolean = true,
        private val left: Boolean = true,
        private val right: Boolean = true
) : Drawable {
    override fun draw(view: CanvasView) {
        val canvas = view.canvas
        if (left || right) (1 until canvas.lastYIndex).forEach { canvasY ->
            val graphY = view.yScaler.canvasToGraph(canvasY.toDouble())
            val canvasElement = CanvasElement(" ${decimalFormat.format(graphY)} ", textStyle)
            if (left) canvas[0, canvasY] = canvasElement
            if (right) canvas[canvas.width - canvasElement.symbols.length, canvasY] = canvasElement
        }
        if (top || bottom) {
            var canvasX = 0
            while (true) {
                val graphX = view.xScaler.canvasToGraph(canvasX.toDouble())
                val canvasElement = CanvasElement("${decimalFormat.format(graphX)} ", textStyle)
                canvasX += canvasElement.symbols.length

                if (canvasX >= canvas.lastXIndex) break
                if (top) canvas[canvasX, 0] = canvasElement
                if (bottom) canvas[canvasX, canvas.lastYIndex] = canvasElement
            }
        }

        val xy = CanvasElement(" Y \\ X", TextStyle(GREEN, intensity = HIGH_INTENSITY))
        canvas[0, canvas.lastYIndex] = xy
        canvas[0, 0] = xy
    }
}