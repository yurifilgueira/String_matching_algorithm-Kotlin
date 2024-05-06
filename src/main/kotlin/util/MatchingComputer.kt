package util

import kotlinx.coroutines.sync.Semaphore


object MatchingComputer {
    private val semaphore = Semaphore(1)

     suspend fun compute(word: String?, matches: MutableMap<String, Int>?) {
        semaphore.acquire()
        if (matches != null) {
            if (word != null) {
                matches[word] = matches.getOrDefault(word, 0) + 1
            }
        }
        semaphore.release()
    }
}