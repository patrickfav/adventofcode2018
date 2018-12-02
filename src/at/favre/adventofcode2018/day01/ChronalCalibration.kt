package at.favre.adventofcode2018.day01

import java.io.File


class FrequencyParser(private val fileName: String) {

    fun calculateSum(): Long {
        val lines = readFileToLines(this.fileName)

        var sum = 0L
        lines.forEach {
            val result = parseLine(it)
            if (result.isMinus) {
                sum -= result.value
            } else {
                sum += result.value
            }
        }
        return sum
    }

    fun firstDuplicateFrequency(): Long {
        val lines = readFileToLines(this.fileName)
        return processDuplicates(lines, HashSet(),0L)
    }

    private fun processDuplicates(lines: List<String>, freqSet: HashSet<Long>, currentFreq: Long): Long {
        var sum = currentFreq
        lines.forEach {
            val result = parseLine(it)
            if (result.isMinus) {
                sum -= result.value
            } else {
                sum += result.value
            }

            if (freqSet.contains(sum)) {
                return sum
            }
            freqSet.add(sum)
        }
        return processDuplicates(lines, freqSet, sum)
    }

    private fun readFileToLines(filename: String): List<String> {
        val file = File(filename)
        if (!file.exists() || !file.isFile) {
            throw IllegalArgumentException("file either does not exist or is not file: $file")
        }
        return file.readLines(Charsets.UTF_8)
    }

    private fun parseLine(line: String): LineResult {
        val copy = line.trim()
        val isMinus = when (copy.codePointAt(0)) {
            '-'.toInt() -> true
            '+'.toInt() -> false
            else -> throw IllegalArgumentException("unexpected sign ${copy.codePointAt(0)}")
        }

        val number = copy.substring(1).toLong()
        return LineResult(isMinus, number);
    }

    private data class LineResult(val isMinus: Boolean, val value: Long);
}

fun main(args: Array<String>) {
    val parser = FrequencyParser(args[0])
    println(parser.calculateSum())
    println(parser.firstDuplicateFrequency())
}
