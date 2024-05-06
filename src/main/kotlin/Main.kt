
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import util.DatasetReader
import util.DistanceCalculator
import util.ResultSaver
import java.util.concurrent.atomic.AtomicInteger

fun main() {

    val blocks = DatasetReader.getBlocks()

    val startTime = System.currentTimeMillis()
    val counter: AtomicInteger = AtomicInteger(0)
    runBlocking {

        val coroutines: MutableList<Deferred<*>> = ArrayList()

        for (i in 0..5) {
            coroutines.add(async(Dispatchers.Default) {DistanceCalculator(blocks.pop(), counter).calculateDistance()})
        }

        runBlocking {
            for (reader in coroutines) {
                reader.await()
            }
        }
    }

    ResultSaver.save(counter)
    println("Total read and print time: " + (System.currentTimeMillis() - startTime).toDouble() / 60000)
    // System.out.println("Count: " + count);
    // matches.forEach { (k: Any, v: Any) -> println("Match: $k - $v") }
}
