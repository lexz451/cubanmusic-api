package info.cubanmusic.cubanmusicapi.helper

import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.*

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
}