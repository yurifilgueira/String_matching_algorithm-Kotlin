package util

import util.MatchingComputer.compute

class DistanceCalculator {
    private var lines: List<String>? = null
    private var matches: MutableMap<String, Int>? = null

    constructor()

    constructor(lines: List<String>?, matches: MutableMap<String, Int>?) {
        this.lines = lines
        this.matches = matches
    }

    fun calculateDistance() {

        println(Thread.currentThread().name + " -> started.")

        for (line in lines!!) {
            val arrayRatingLine = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val rating = arrayRatingLine[2].replace("\"".toRegex(), "").toLowerCase()
            val words = rating.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (word in words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    compute(word, matches)
                }
            }
        }

        println(Thread.currentThread().name + " -> finished.")
    }
}