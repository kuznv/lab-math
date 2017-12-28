package graph.canvas

import graph.scaler.GraphScaler
import graph.scaler.Scaler
import util.DoubleRange
import util.toDoubleRange

data class CanvasView(
        val canvas: Canvas,
        val xRange: DoubleRange = -canvas.width / 2.0..canvas.width / 2.0,
        val yRange: DoubleRange = -canvas.height / 2.0..canvas.height / 2.0
) {
    val xScaler: GraphScaler
    val yScaler: GraphScaler

    init {
        val canvasXToGraph = Scaler(
                originalRange = canvas.xIndices.toDoubleRange(),
                targetRange = xRange
        )
        val canvasYToGraph = Scaler(
                originalRange = canvas.yIndices.toDoubleRange(),
                targetRange = yRange
        )
        xScaler = GraphScaler(
                canvasToGraph = canvasXToGraph,
                graphToCanvas = !canvasXToGraph
        )
        yScaler = GraphScaler(
                canvasToGraph = canvasYToGraph,
                graphToCanvas = !canvasYToGraph
        )
    }
}