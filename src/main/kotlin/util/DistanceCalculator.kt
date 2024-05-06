package util

import java.util.concurrent.atomic.AtomicInteger

class DistanceCalculator {
    private var lines: List<String>? = null
    private var counter: AtomicInteger? = null

    constructor()

    constructor(lines: List<String>?, counter: AtomicInteger?) {
        this.lines = lines
        this.counter = counter
    }

    suspend fun calculateDistance() {

        println(Thread.currentThread().name + " -> started.")

        for (line in lines!!) {
            val arrayRatingLine = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val rating = arrayRatingLine[2].replace("\"".toRegex(), "").toLowerCase()
            val words = rating.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (word in words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    counter?.incrementAndGet()
                }
            }
        }

        println(Thread.currentThread().name + " -> finished.")
    }
}