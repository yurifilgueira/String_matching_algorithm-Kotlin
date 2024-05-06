import util.DatasetReader
import util.DistanceCalculator
import java.io.IOException


@Throws(IOException::class)
fun main() {
    val lines: List<String> = DatasetReader.readFile()

    val startTime = System.currentTimeMillis()

    DistanceCalculator.calculateDistance(lines)

    println("Total read and print time: " + (System.currentTimeMillis() - startTime).toDouble() / 1000)
}
