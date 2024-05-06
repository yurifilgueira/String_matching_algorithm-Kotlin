package util

import java.util.concurrent.Semaphore

object MatchingComputer {
    private val semaphore = Semaphore(1)

     fun compute(word: String?, matches: MutableMap<String, Int>?) {
        semaphore.acquire()
        if (matches != null) {
            if (word != null) {
                matches[word] = matches.getOrDefault(word, 0) + 1
            }
        }
        semaphore.release()
    }
}