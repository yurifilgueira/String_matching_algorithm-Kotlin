
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import readers.Reader
import util.DistanceCalculator
import util.ResultSaver

fun main() {

    val startTime = System.currentTimeMillis()

    val blocks = Reader.getBlocks()

    val matches: MutableMap<String, Int> = HashMap()

    runBlocking {

        val coroutines: MutableList<Deferred<*>> = ArrayList()

        for (i in 0..5) {
            coroutines.add(async(Dispatchers.Default) {DistanceCalculator(blocks.pop(), matches).calculateDistance()})
        }

        runBlocking {
            for (reader in coroutines) {
                reader.await()
            }
        }
    }

    ResultSaver.save(matches)
    println("Total read and print time: " + (System.currentTimeMillis() - startTime).toDouble() / 1000)
    // System.out.println("Count: " + count);
    // matches.forEach { (k: Any, v: Any) -> println("Match: $k - $v") }
}
