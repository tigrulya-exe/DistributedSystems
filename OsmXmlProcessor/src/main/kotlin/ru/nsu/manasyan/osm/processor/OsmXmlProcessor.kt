package ru.nsu.manasyan.osm.processor

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import ru.nsu.manasyan.osm.model.OsmStatistics
import java.io.BufferedInputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

abstract class OsmXmlProcessor {
    fun process(fileName: String): OsmStatistics {
        return getDecompressedBZ2Stream(fileName).use {
            processDecompressedStream(it)
        }
    }

    protected abstract fun processDecompressedStream(stream: InputStream): OsmStatistics

    private fun getDecompressedBZ2Stream(fileName: String) =
        BZip2CompressorInputStream(
            BufferedInputStream(
                Files.newInputStream(
                    Path.of(fileName),
                )
            )
        )
}