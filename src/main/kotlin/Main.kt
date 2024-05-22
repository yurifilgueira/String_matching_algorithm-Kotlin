
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import util.DatasetReader
import util.DistanceCalculator
import util.ResultSaver
import java.util.concurrent.Executors

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val blocks = DatasetReader.getBlocks()

    val threadPoolSize = 6
    val dispatcher = Executors.newFixedThreadPool(threadPoolSize).asCoroutineDispatcher()

    val deferredResults = blocks.map { block ->
        async(dispatcher) {
            try {
                val calculator = DistanceCalculator(block)
                calculator.call()
            } catch (e: Exception) {
                println("Erro ao calcular distância: ${e.message}")
                0
            }
        }
    }

    val total = deferredResults.awaitAll().sum()

    ResultSaver.save(total)
    println("Tempo total de execução: ${(System.currentTimeMillis() - startTime) / 1000.0} segundos")

    dispatcher.close()
}
