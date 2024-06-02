package util

import util.ResultSaver.save

object DistanceCalculator {
    fun calculateDistance(lines: List<String>): Int {

        var count = 0
        for (line in lines) {
            val words = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (word in words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    count += 1
                }
            }
        }

        return count
    }
}