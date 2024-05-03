
import util.DatasetReader
import util.DistanceCalculator
import util.ResultSaver.save
import java.io.IOException
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread


@Throws(InterruptedException::class, IOException::class)
fun main() {
    val threads: MutableList<Thread> = ArrayList()

    val blocks: Stack<List<String>> = DatasetReader.getBlocks()

    println("Starting threads...")

    val startTime = System.currentTimeMillis()

    val counter = AtomicInteger(0)
    for (i in 0..5) {

        threads.add(thread (start = true, isDaemon = false, name = String.format("%02d", i), priority = 1) {
            val t = DistanceCalculator(blocks.pop(), counter)
            t.calculateDistance()
        })
    }

    for (thread in threads) {
        thread.join()
    }

    println("Total read and print time: " + (System.currentTimeMillis() - startTime).toDouble() / 60000)

    save(counter)
}