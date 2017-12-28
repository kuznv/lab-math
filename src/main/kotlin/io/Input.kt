package io

import java.io.InputStream
import java.text.ParseException

open class Input(val inputStream: InputStream) : AutoCloseable by inputStream {
    open fun <T> parse(message: String, parse: Input.() -> T): T = parse()
}

object UserInput : Input(System.`in`) {
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