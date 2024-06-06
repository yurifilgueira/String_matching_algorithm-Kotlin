package util

import java.util.concurrent.atomic.AtomicInteger

class DistanceCalculator {

     fun calculateDistance(lines: List<String>?, counter: AtomicInteger) {

        println(Thread.currentThread().name + " -> started.")

        for (line in lines!!) {
            val words = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (word in words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    counter.incrementAndGet()
                }
            }
        }

        println(Thread.currentThread().name + " -> finished.")
    }
}
