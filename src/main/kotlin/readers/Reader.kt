package readers

import java.io.IOException
import java.nio.file.Files.newBufferedReader
import java.nio.file.Path
import java.util.*

object Reader {
    private const val PATH: String = "resources/train.csv"

    @Throws(IOException::class)
    fun readFile(): List<String> {

        val bf = newBufferedReader(Path.of(PATH))
        try {

            val lines = arrayListOf<String>()

            var line: String?

            while ((bf.readLine().also { line = it }) != null) {
                val arrayRatingLine = line!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val rating = arrayRatingLine[2].replace("\"".toRegex(), "").toLowerCase()

                lines.add(rating)

            }

            return lines
        }catch (e: IOException) {
            throw IOException(e.message)
        } finally {
            bf.close()
        }
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