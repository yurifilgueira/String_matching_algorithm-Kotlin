package util

import util.ResultSaver.save

object DistanceCalculator {
    fun calculateDistance(lines: List<String>) {
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
                "laptop"
            )
        )
        val matches: MutableMap<String, Int> = HashMap()

        for (line in lines) {
            val arrayRatingLine = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val rating = arrayRatingLine[2].replace("\"".toRegex(), "").toLowerCase()
            val words = rating.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (item in items) {
                for (word in words) {
                    if (LevenshteinDistance.calculateDistance(word, item) === 0) {
                        matches[word] = matches.getOrDefault(word, 0) + 1
                        break
                    }
                }
            }
        }

        save(matches)
    }
}