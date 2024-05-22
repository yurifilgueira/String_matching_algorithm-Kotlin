package util

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object ResultSaver {
    private  const val PATH = "resources\\results.txt"

    fun save(count: Int) {
        try {
            Files.newBufferedWriter(Paths.get(PATH)).use { bw ->
                bw.write("Quantity of matches:")
                bw.newLine()
                bw.write(count.toString())
                bw.newLine()
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}