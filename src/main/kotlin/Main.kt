import readers.Reader
import util.DistanceCalculator
import util.ResultSaver
import java.io.IOException


@Throws(IOException::class)
fun main() {
    val startTime = System.currentTimeMillis()

    val lines: List<String> = Reader.readFile()
    val result = DistanceCalculator.calculateDistance(lines)

    ResultSaver.save(result)

    println(result)
    println("Total read and print time: " + (System.currentTimeMillis() - startTime).toDouble() / 1000)
}
