
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import util.DatasetReader
import util.DistanceCalculator
import util.ResultSaver

fun main() {

    val blocks = DatasetReader.getBlocks()

    val startTime = System.currentTimeMillis()
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
    println("Total read and print time: " + (System.currentTimeMillis() - startTime).toDouble() / 60000)
    // System.out.println("Count: " + count);
    matches.forEach { (k: Any, v: Any) -> println("Match: $k - $v") }
}
