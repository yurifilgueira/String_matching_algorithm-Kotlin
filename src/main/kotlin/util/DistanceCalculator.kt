package util

import util.ResultSaver.save

object DistanceCalculator {
    fun calculateDistance(lines: List<String>) {

        val matches: MutableMap<String, Int> = HashMap()

        for (line in lines) {
            val arrayRatingLine = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val rating = arrayRatingLine[2].replace("\"".toRegex(), "").toLowerCase()
            val words = rating.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (word in words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    matches[word] = matches.getOrDefault(word, 0) + 1
                }
            }
        }

        save(matches)
    }
}