
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import util.DatasetReader
import java.util.concurrent.ConcurrentHashMap

fun main(args: Array<String>) {
    val PATH = "resources\\train.csv"

    val startTime = System.currentTimeMillis()

    val items: List<String> = ArrayList(
        listOf(
            "logitech",
            "keyboard",
            "mouse",
            "hyperx",
            "razer",
            "lenovo",
            "acer",
            "lg",
            "samsung",
            "laptop"
        )
    )

    val datasetReaders: MutableList<DatasetReader> = ArrayList()

    val matches: ConcurrentHashMap<String, Int> = ConcurrentHashMap<String, Int>()
    runBlocking {
        var i = 0
        while (i < items.size) {
            val datasetReader = DatasetReader(items[i], items[++i], matches)
            datasetReaders.add(datasetReader)
            i++
        }

        val coroutines: MutableList<Deferred<*>> = ArrayList()

        for (datasetReader in datasetReaders) {
            coroutines.add(async(Dispatchers.Default) {datasetReader.run()})
        }

        runBlocking {
            for (reader in coroutines) {
                reader.await()
            }
        }
    }

    println("Total read and print time: " + (System.currentTimeMillis() - startTime).toDouble() / 60000)
    // System.out.println("Count: " + count);
    matches.forEach { (k: Any, v: Any) -> println("Match: $k - $v") }
}
