package util

import util.MatchingComputer.compute
import java.util.concurrent.locks.ReentrantLock

class DistanceCalculator {
    private var lines: List<String>? = null
    private var matches: MutableMap<String, Int>? = null
    private val mutex = ReentrantLock()

    constructor()

    constructor(lines: List<String>?, matches: MutableMap<String, Int>?) {
        this.lines = lines
        this.matches = matches
    }

    fun calculateDistance() {
        val items: List<String> = ArrayList(
            listOf(
                "logitech",
                "keyboard",
                "mouse",
                "hyperx",
                "razer",
                "lenovo",
                "acer",
                "lg",
                "samsung",
                "laptop",
                "headset",
                "jbl"
            )
        )

        println(Thread.currentThread().name + " -> started.")

        for (line in lines!!) {
            val arrayRatingLine = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val rating = arrayRatingLine[2].replace("\"".toRegex(), "").toLowerCase()
            val words = rating.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (item in items) {
                for (word in words) {
                    if (LevenshteinDistance.calculateDistance(word, item) == 0) {
                        compute(word, matches)
                        break
                    }
                }
            }
        }

        println(Thread.currentThread().name + " -> finished.")
    }
}