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

    fun String?.parseDate(): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return try {
            formatter.parse(this)
        } catch (e: Exception) {
            logger.error("Invalid date received. setting it to null...")
            null
        }
    }

    fun Date?.formatDate(): String? {
        this?.let {
            val format = SimpleDateFormat("YYYY-MM-dd")
            return format.format(this)
        }
        return null
    }

    @Suppress("SpellCheckingInspection")
    fun ByteArray.compress(): ByteArray {
        val deflater = Deflater()
        deflater.setInput(this)
        deflater.finish()
        val outputStream = ByteArrayOutputStream(this.size)
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


    fun ByteArray.decompress(): ByteArray? {
        val inflater = Inflater()
        inflater.setInput(this)
        val outputStream = ByteArrayOutputStream(this.size)
        val buffer = ByteArray(1024)
        try {
            while (!inflater.finished()) {
                val count = inflater.inflate(buffer)
                outputStream.write(buffer, 0, count)
            }
            outputStream.close()
        } catch (ioe: IOException) {
            logger.error(ioe.message)
        } catch (e: DataFormatException) {
            logger.error(e.message);
        }
        return outputStream.toByteArray()
    }
}