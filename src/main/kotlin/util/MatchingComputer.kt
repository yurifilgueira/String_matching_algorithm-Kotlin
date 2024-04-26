package util

import kotlinx.coroutines.sync.Mutex

object MatchingComputer {
    private val mutex = Mutex()

    suspend fun compute(word: String?, matches: MutableMap<String, Int>?) {
        mutex.lock()
        if (matches != null) {
            if (word != null) {
                matches[word] = matches.getOrDefault(word, 0) + 1
            }
        }
        mutex.unlock()
    }
}