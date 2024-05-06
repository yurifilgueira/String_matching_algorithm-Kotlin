package util

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object ResultSaver {
    private const val PATH = "resources\\results.txt"

    fun save(matches: MutableMap<String, Int>) {
        try {
            Files.newBufferedWriter(Paths.get(PATH)).use { bw ->
                bw.write("Quantity of matches:")
                bw.newLine()

                matches.entries.forEach {
                    bw.write(it.key + " -> " + "${it.value}");
                }
                bw.newLine()
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}