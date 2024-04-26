package util

import java.util.concurrent.locks.ReentrantLock

object MatchingComputer {
    private val mutex = ReentrantLock()

    fun compute(word: String?, matches: MutableMap<String, Int>?) {
        mutex.lock()
        if (matches != null) {
            if (word != null) {
                matches[word] = matches.getOrDefault(word, 0) + 1
            }
        }
        mutex.unlock()
    }
}