package io

import java.io.InputStream

interface Input {
    val inputStream: InputStream
    fun onException(message: String)
}

class StreamInput(override val inputStream: InputStream) : Input, AutoCloseable by inputStream {
    override fun onException(message: String): Nothing {
        throw ParseException(message)
    }
}

object UserInput : Input {
    override val inputStream: InputStream = System.`in`

    override fun onException(message: String) {
        System.err.println(message)
    }
}