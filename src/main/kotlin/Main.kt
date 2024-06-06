
import readers.Reader
import util.DistanceCalculator
import util.ResultSaver.save
import java.io.IOException
import java.util.*
import kotlin.concurrent.thread


@Throws(InterruptedException::class, IOException::class)
fun main() {

    val startTime = System.currentTimeMillis()

    val threads: MutableList<Thread> = ArrayList()

    val matches: MutableMap<String, Int> = HashMap()

    val blocks: Stack<List<String>> = Reader.getBlocks()

    println("Starting threads...")

    for (i in 0..5) {

        threads.add(thread (start = true, isDaemon = false, name = String.format("%02d", i), priority = 1) {
            val t = DistanceCalculator(blocks.pop(), matches)
            t.calculateDistance()
        })
    }

    for (thread in threads) {
        thread.join()
    }

    println("Total read and print time: " + (System.currentTimeMillis() - startTime).toDouble() / 1000)

    save(matches)
}
