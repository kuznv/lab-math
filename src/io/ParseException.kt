package io

class ParseException(
        errorMessage: String = "",
        errorOffset: Int = 0
) : java.text.ParseException(errorMessage, errorOffset) {

    constructor(errorMessage: String, errorLine: String, errorOffset: Int) : this(
            getMessage(errorMessage, errorLine, errorOffset),
            errorOffset
    )

    companion object {
        fun getMessage(errorMessage: String = "",
                       errorLine: String = "",
                       errorOffset: Int
        ): String {
            val errorLineFormatted = errorLine.replace(Regex("""[\n\t\f ]"""), " ")

            return "$errorMessage\n$errorLineFormatted\n${String(CharArray(errorOffset) { ' ' })}^"
        }
    }
}