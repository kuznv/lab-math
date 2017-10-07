package io

import util.times

class ParseException(
        errorMessage: String = "",
        errorOffset: Int = -1
) : java.text.ParseException(errorMessage, errorOffset) {

    constructor(errorMessage: String, errorLine: String, errorOffset: Int) : this(
            getMessage(errorMessage, errorLine, errorOffset),
            errorOffset
    )

    companion object {
        fun getMessage(errorMessage: String = "",
                       errorLine: String = "",
                       errorOffset: Int = -1
        ) = "$errorMessage\n$errorLine\n${String(' ' * errorOffset)}^"
    }
}