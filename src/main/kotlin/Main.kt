
import util.DatasetReader
import util.DistanceCalculator
import util.ResultSaver.save
import java.io.IOException
import java.util.*

@Throws(InterruptedException::class, IOException::class)
fun main() {
    val threads: MutableList<Thread> = ArrayList()

    val matches: MutableMap<String, Int> = HashMap()

    val blocks: Stack<List<String>> = DatasetReader.getBlocks()

    println("Starting threads...")

    val startTime = System.currentTimeMillis()

    for (i in 0..5) {
        val t = Thread(DistanceCalculator(blocks.pop(), matches))
        threads.add(t)
        t.start()
    }

    for (thread in threads) {
        thread.join()
    }

    println("Total read and print time: " + (System.currentTimeMillis() - startTime).toDouble() / 60000)

    save(matches)
}
