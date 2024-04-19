package util

import kotlinx.coroutines.sync.Mutex
import util.ResultSaver.save
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class DatasetReader {
    private var item: String? = null
    private var item2: String? = null
    private var matches: MutableMap<String, Int>? = null
    private val mutex = Mutex()

    constructor()

    constructor(item: String?, matches: MutableMap<String, Int>?) {
        this.item = item
        this.matches = matches
    }

    constructor(item: String?, item2: String?, matches: MutableMap<String, Int>?) {
        this.item = item
        this.item2 = item2
        this.matches = matches
    }

    private suspend fun readDatasetAndProcess() {

        try {
            Files.newBufferedReader(Paths.get(PATH)).use { br ->
                var counter = 0
                var line: String?
                while ((br.readLine().also { line = it }) != null) {
                    counter++
                    val arrayRatingLine =
                        line?.split(",".toRegex())?.dropLastWhile { it.isEmpty() }
                            ?.toTypedArray()
                    val rating =
                        arrayRatingLine?.get(2)?.replace("\"".toRegex(), "")?.toLowerCase()
                    val words =
                        rating?.split(" ".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()

                    if (words != null) {
                        for (word in words) {
                            if (LevenshteinDistance.calculateDistance(item!!, word) == 0) {
                                println("Match: $item - $word")

                                mutex.lock()
                                matches!![word] = matches!!.getOrDefault(word, 0) + 1
                                mutex.unlock()

                                break
                            } else if (LevenshteinDistance.calculateDistance(item2!!, word) == 0) {
                                println("Match: $item2 - $word")

                                mutex.lock()
                                matches!![word] = matches!!.getOrDefault(word, 0) + 1
                                mutex.unlock()

                                break
                            }
                        }
                    }
                }

                println("Count: $counter")
                matches?.let { save(it) }
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    suspend fun run() {
        val startTime = System.currentTimeMillis()
        readDatasetAndProcess()
        println("Total read and print time of " + Thread.currentThread().name + ": " + (System.currentTimeMillis() - startTime).toDouble() / 60000)
    }

    companion object {
        private const val PATH = "resources\\train.csv"
    }
}