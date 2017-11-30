package io

import java.io.InputStream

interface Input {
    val inputStream: InputStream

    fun <T> parse(message: String, parse: Input.() -> T): T = parse()
}

class StreamInput(override val inputStream: InputStream) : Input, AutoCloseable by inputStream

object UserInput : Input {
    override val inputStream: InputStream = System.`in`

    override fun <T> parse(message: String, parse: Input.() -> T): T {
        while (true) {
            print(message)

            try {
                return parse(this)
            } catch (e: ParseException) {
                System.err.println(e.message)
            }
        }
    }
}