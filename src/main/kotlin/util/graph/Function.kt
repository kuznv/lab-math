package util.graph

data class Function(
        val drawParams: DrawParams,
        val f: (Double) -> Double
) : Drawable {

    override fun Graph.draw() {

    }
}