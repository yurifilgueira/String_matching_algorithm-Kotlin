package util

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class DatasetReader {
    private var PATH: String? = null

    constructor()

    constructor(PATH: String?) {
        this.PATH = PATH
    }

    companion object {
        private val lines: List<String>? = null

        fun readDatasetAndProcess(PATH: String?, items: List<String>) {
            val matches: MutableMap<String, Int> = HashMap()

            try {
                Files.newBufferedReader(PATH?.let { Paths.get(it) }).use { br ->
                    var counter = 0
                    var line: String?
                    while ((br.readLine().also { line = it }) != null) {
                        counter++
                        val arrayRatingLine =
                            line?.split(",".toRegex())?.dropLastWhile { it.isEmpty() }
                                ?.toTypedArray()
                        val rating =
                            arrayRatingLine?.get(2)?.replace("\"".toRegex(), "")?.lowercase(Locale.getDefault())
                        val words =
                            rating?.split(" ".toRegex())?.dropLastWhile { it.isEmpty() }
                                ?.toTypedArray()

                        for (item in items) {
                            if (words != null) {
                                for (word in words) {
                                    if (LevenshteinDistance.calculateDistance(item, word) == 0) {
                                        matches[word] = matches.getOrDefault(word, 0) + 1
                                        println("Match: $item - $word")
                                        break
                                    }
                                }
                            }
                        }
                    }

                    println("Count: $counter")
                    matches.forEach { (k: String, v: Int) -> println("Match: $k - $v") }
                    ResultSaver.save(matches)
                }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
    }
}