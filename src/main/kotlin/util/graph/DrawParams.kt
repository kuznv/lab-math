package util.graph

import util.orEmpty

class DrawParams(
        val symbol: String,
        val color: AnsiCode.Color? = null,
        val background: AnsiCode.BackgroundColor? = null,
        val intensity: AnsiCode.Intensity? = null,
        vararg val params: AnsiCode.Param
) {
    override fun toString() =
            if (color == null && background == null && intensity == null && params.isEmpty())
                symbol
            else
                "${color.orEmpty()}${background.orEmpty()}${intensity.orEmpty()}" +
                        "${params.joinToString(separator = "")}$symbol${AnsiCode.SANE}"
}