package readers

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Files.newBufferedReader
import java.nio.file.Path
import java.nio.file.Paths

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
                val rating = arrayRatingLine[2].replace("\"".toRegex(), "").lowercase()

                lines.add(rating)

            }

            return lines
        }catch (e: IOException) {
            throw IOException(e.message)
        } finally {
            bf.close()
        }
    }
}