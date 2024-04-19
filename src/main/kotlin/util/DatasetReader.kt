package util

import util.ResultSaver.save
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class DatasetReader : Runnable {
    private val lock: Lock = ReentrantLock()

    private var item: String? = null
    private var item2: String? = null
    private var matches: MutableMap<String, Int>? = null

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

    fun readDatasetAndProcess() {
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
                                lock.lock()
                                matches!![word] = matches!!.getOrDefault(word, 0) + 1
                                lock.unlock()

                                println("Match: $item - $word")
                                break
                            } else if (LevenshteinDistance.calculateDistance(item2!!, word) == 0) {
                                lock.lock()
                                matches!![word] = matches!!.getOrDefault(word, 0) + 1
                                lock.unlock()

                                println("Match: $item2 - $word")
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
        }
    }


    override fun run() {
        val startTime = System.currentTimeMillis()
        readDatasetAndProcess()
        println("Total read and print time of " + Thread.currentThread().name + ": " + (System.currentTimeMillis() - startTime).toDouble() / 60000)
    }

    companion object {
        private const val PATH = "resources\\train.csv"
    }
}