package util

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.atomic.AtomicInteger

object ResultSaver {
    private const val PATH = "resources\\results.txt"

    fun save(counter: AtomicInteger) {
        try {
            Files.newBufferedWriter(Paths.get(PATH)).use { bw ->
                bw.write("Quantity of matches:")
                bw.newLine()

                bw.write(counter.toString())
                bw.newLine()
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}