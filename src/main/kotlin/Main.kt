import util.DatasetReader

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

    DatasetReader.readDatasetAndProcess(PATH, items)

    println("Total read and print time: " + (System.currentTimeMillis() - startTime).toDouble() / 60000)
}
