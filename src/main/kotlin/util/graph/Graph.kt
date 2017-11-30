package util.graph

class Graph {
    var width = 185
    var height = 36

    var xMin = 0
    var xMax = 10

    val xRange get() = 0 until width
    val yRange get() = 0 until height

    val drawables: MutableList<Drawable> = ArrayList()

/*
    fun paint(xMin: Double, xMax: Double) {
        val buffer = MutableList(height) { MutableList(width) { " " } }
        val step = (xMax - xMin) / (width - 1)

        val ys = drawables.map { (_, _, f) ->
            generateSequence(xMin) { x -> x + step }
                    .take(width)
                    .map(f)
                    .toList()
        }

        val allYs = ys.flatten()
        val max = allYs.max() ?: 10.0
        val min = allYs.min() ?: -10.0
        val scale = (height - 1) / (max - min)

        for ((f, function) in ys.zip(functions)) {
            val (symbol, color) = function

            f.forEachIndexed { i, y ->
                val newY = height - (y - min) * scale - 1
                buffer[newY.roundToInt()][i] = "$color$symbol${AnsiCode.SANE}"
            }
        }

        buffer.forEach { println(it.joinToString(separator = "")) }
    }
*/
}