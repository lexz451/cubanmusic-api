package info.cubanmusic.cubanmusicapi.helper

import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.DataFormatException
import java.util.zip.Deflater
import java.util.zip.Inflater


object Utils {

    private val logger = LoggerFactory.getLogger(Utils::class.java)

    fun parseDate(dateString: String?): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return try {
            formatter.parse(dateString)
        } catch (e: Exception) {
            logger.error("Invalid date received. Returning null...")
            null
        }
    }

    fun formatDate(date: Date?): String? {
        date?.let {
            val format = SimpleDateFormat("YYYY-MM-dd")
            return format.format(date)
        }
        return null
    }

    fun compressBytes(data: ByteArray): ByteArray {
        val deflater = Deflater()
        deflater.setInput(data)
        deflater.finish()
        val outputStream = ByteArrayOutputStream(data.size)
        val buffer = ByteArray(1024)
        while (!deflater.finished()) {
            val count = deflater.deflate(buffer)
            outputStream.write(buffer, 0, count)
            try {
                outputStream.close()
            } catch (ioe: IOException) {

            }
        }
        return outputStream.toByteArray()
    }


    fun decompressBytes(data: ByteArray): ByteArray? {
        val inflater = Inflater()
        inflater.setInput(data)
        val outputStream = ByteArrayOutputStream(data.size)
        val buffer = ByteArray(1024)
        try {
            while (!inflater.finished()) {
                val count = inflater.inflate(buffer)
                outputStream.write(buffer, 0, count)
            }
            outputStream.close()
        } catch (ioe: IOException) {
        } catch (e: DataFormatException) {
        }
        return outputStream.toByteArray()
    }
}