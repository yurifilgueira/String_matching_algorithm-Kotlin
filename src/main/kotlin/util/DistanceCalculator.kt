package util

import java.util.concurrent.Callable

class DistanceCalculator : Callable<Int> {
    private var lines: List<String>? = null

    constructor()

    constructor(lines: List<String>?) {
        this.lines = lines
    }

    private fun calculateDistance(): Int {
        println(Thread.currentThread().name + " -> started.")

        var count = 0
        for (line in lines!!) {
            val words = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (word in words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    count += 1
                }
            }
        }

        println(Thread.currentThread().name + " -> finished.")
        return count
    }

    override fun call(): Int {
        return calculateDistance()
    }
}