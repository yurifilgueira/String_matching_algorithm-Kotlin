package util

object MatchingComputer {
    private val semaphore = java.util.concurrent.Semaphore(1)

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