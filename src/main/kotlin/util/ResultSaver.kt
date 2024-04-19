package util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object ResultSaver {
    private const val PATH = "resources\\results.txt"
    private val mutex = Mutex()

    suspend fun save(matches: MutableMap<String, Int>) {
        mutex.lock()
        try {
            withContext(Dispatchers.IO) {
                Files.newBufferedWriter(Paths.get(PATH))
            }.use { bw ->
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
        } finally {
            mutex.unlock()
        }
    }
}