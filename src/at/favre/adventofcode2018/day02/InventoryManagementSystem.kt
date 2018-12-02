package at.favre.adventofcode2018.day02

import java.io.File

class BoxIdChecksumGenerator(private val fileName: String) {

    fun getMatchingBoxId(): String {
        val lines = readFileToLines(this.fileName)

        for (i in 0 until lines.size) {
            for (j in 0 until lines[i].length) {
                val candidate = createCandidate(lines[i], j)

                for (k in i+1 until lines.size) {
                    if (candidate == createCandidate(lines[k], j)) {
                        return candidate
                    }
                }
            }
        }
        throw IllegalArgumentException("no matching box id found")
    }

    private fun createCandidate(line: String, index: Int) = line.removeRange(index, index + 1)

    fun generateChecksum(): Long {
        val lines = readFileToLines(this.fileName)
        var doublesCount = 0L
        var triplesCount = 0L
        lines.forEach {
            val result = calculateChecksum(it)
            doublesCount += result.doublesCount
            triplesCount += result.triplesCount
        }

        return doublesCount * triplesCount
    }

    private fun calculateChecksum(line: String): LineResult {
        val freqMap = HashMap<Int, Long?>()
        line.chars().forEach {
            freqMap[it] = if (freqMap[it] == null) 1 else freqMap[it]?.plus(1)
        }
        //println(line)
        val result = LineResult(0, 0)
        freqMap.forEach { key, value ->
            if (value == 2L) {
                result.doublesCount = 1
                //println("double: ${key.toChar()}")
            } else if (value == 3L) {
                result.triplesCount = 1
                //println("triple: ${key.toChar()}")
            }
        }

        return result
    }

    private fun readFileToLines(filename: String): List<String> {
        val file = File(filename)
        if (!file.exists() || !file.isFile) {
            throw IllegalArgumentException("file either does not exist or is not file: $file")
        }
        return file.readLines(Charsets.UTF_8)
    }


    data class LineResult(var doublesCount: Long, var triplesCount: Long)
}

fun main(args: Array<String>) {
    val gen = BoxIdChecksumGenerator(args[0])
    println(gen.generateChecksum())
    println(gen.getMatchingBoxId())
}
