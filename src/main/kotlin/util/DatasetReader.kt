package util

import util.ResultSaver.save
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.ConcurrentHashMap

class DatasetReader {
    private var item: String? = null
    private var item2: String? = null
    private var matches: ConcurrentHashMap<*, *>? = null

    constructor()

    constructor(item: String?, matches: ConcurrentHashMap<*, *>?) {
        this.item = item
        this.matches = matches
    }

    constructor(item: String?, item2: String?, matches: ConcurrentHashMap<*, *>?) {
        this.item = item
        this.item2 = item2
        this.matches = matches
    }

    private fun readDatasetAndProcess() {

        var count = 0
        var count1 = 0
        try {
            Files.newBufferedReader(Paths.get(PATH)).use { br ->
                var counter = 0
                var line: String?
                Thread.sleep(1000)
                println(Thread.currentThread().name + " começou.")
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
                            if (LevenshteinDistance.calculateDistance(item!!, word) === 0) {
                                count++
                                println("Match: $item - $word")
                                break
                            } else if (LevenshteinDistance.calculateDistance(item2!!, word) === 0) {
                                count1++
                                println("Match: $item2 - $word")
                                break
                            }
                        }
                    }
                }

                println("Count: $counter")
                save(count, count1)
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