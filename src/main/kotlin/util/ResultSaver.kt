package util

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object ResultSaver {
    private const val PATH = "resources\\results.txt"

    fun save(cout: Int, cout1: Int) {
        try {
            Files.newBufferedWriter(Paths.get(PATH)).use { bw ->
                bw.write("Quantity of matches:")
                bw.newLine()

                bw.write(Thread.currentThread().name + cout.toString())
                bw.newLine()
                bw.write(Thread.currentThread().name + cout1.toString())

            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}