package util

class DistanceCalculator {
    private var lines: List<String>? = null
    private var matches: MutableMap<String, Int>? = null

    constructor()

    constructor(lines: List<String>?, matches: MutableMap<String, Int>?) {
        this.lines = lines
        this.matches = matches
    }

    suspend fun calculateDistance() {

        println(Thread.currentThread().name + " -> started.")

        for (line in lines!!) {
            val words = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (word in words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    MatchingComputer.compute(word, matches)
                }
            }
        }

        println(Thread.currentThread().name + " -> finished.")
    }
}