package util

import java.io.File
import java.io.IOException
import java.util.*

object DatasetReader {
    const val PATH: String = "resources\\train.csv"

    @Throws(IOException::class)
    fun readFile(): List<String> {
        return File(PATH).useLines { it.toList() }
    }

    @Throws(IOException::class)
    fun getBlocks(): Stack<List<String>> {

        val file = readFile()

        val stack = Stack<List<String>>()
        var i = 0
        while (i < 3600000) {
            stack.push(file.subList(i, i + 600000))
            i += 600000
        }

        return stack
    }

}