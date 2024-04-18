package util

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object ResultSaver {
    private const val PATH = "resources\\results.txt"

    fun save(matches: Map<String, Int>) {
        try {
            Files.newBufferedWriter(Paths.get(PATH)).use { bw ->
                bw.write("Quantity of matches:")
                bw.newLine()
                matches.forEach { (k: String, v: Int) ->
                    try {
                        bw.write("$k -> $v")
                        bw.newLine()
                    } catch (e: IOException) {
                        throw RuntimeException(e)
                    }
                }
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}
