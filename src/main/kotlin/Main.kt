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

        val threads: MutableList<Thread> = ArrayList()

        val matches: ConcurrentHashMap<String, Int> = ConcurrentHashMap<String, Int>()

        var i = 0
        while (i < items.size) {
            val t = Thread(DatasetReader(items[i], items[++i], matches))
            t.start()
            threads.add(t)
            i++
        }

        for (thread in threads) {
            thread.join()
        }

        println("Total read and print time: " + (System.currentTimeMillis() - startTime).toDouble() / 60000)
        // System.out.println("Count: " + count);
        matches.forEach { (k: Any, v: Any) -> println("Match: $k - $v") }
 }
