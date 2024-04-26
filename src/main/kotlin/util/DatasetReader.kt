package util

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object DatasetReader {
    const val PATH: String = "resources\\train.csv"

    @Throws(IOException::class)
    fun readFile(): List<String> {
        return Files.readAllLines(Paths.get(PATH))
    }
}