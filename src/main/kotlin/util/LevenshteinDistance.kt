package util

import kotlin.math.min

class LevenshteinDistance {
    var origin: String? = null
    var destiny: String? = null

    constructor()

    constructor(origin: String?, destiny: String?) {
        this.origin = origin
        this.destiny = destiny
    }

    companion object {
        fun calculateDistance(origin: String, destiny: String): Int {
            val rows = origin.length
            val columns = destiny.length
            val distance = Array(rows + 1) { IntArray(columns + 1) }

            for (i in 0..rows) {
                distance[i][0] = i
            }

            for (i in 0..columns) {
                distance[0][i] = i
            }

            for (i in 1..rows) {
                for (j in 1..columns) {
                    if (origin[i - 1] == destiny[j - 1]) {
                        distance[i][j] = distance[i - 1][j - 1]
                    } else {
                        distance[i][j] = (1 + min(
                            distance[i][j - 1].toDouble(),
                            min(
                                distance[i - 1][j].toDouble(),
                                distance[i - 1][j - 1].toDouble()
                            )
                        )).toInt()
                    }
                }
            }

            return distance[rows][columns]
        }
    }
}