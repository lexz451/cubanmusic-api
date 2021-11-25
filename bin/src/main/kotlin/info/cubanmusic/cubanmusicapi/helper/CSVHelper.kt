package info.cubanmusic.cubanmusicapi.helper

import info.cubanmusic.cubanmusicapi.model.Country
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.time.measureTime

object CSVHelper {

    fun csvToCountries(inputStream: InputStream): List<Country> {
        val countries = mutableListOf<Country>()
        try {
            val fileReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            val parser = CSVParser(
                fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
            val records = parser.records
            records.forEach { record ->
                val country = Country()
                country.name = record.get("name").toString()
                country.iso2Code = record.get("iso2").toString()
                country.iso3Code = record.get("iso3").toString()
                country.numericCode = record.get("numeric_code").toString()
                country.phoneCode = record.get("phone_code").toString()
                country.emoji = record.get("emoji").toString()
                countries.add(country)
            }
        } catch (e: Exception) {
            throw RuntimeException("CSV data parsing failed. Check your definitions...!")
        }
        return countries
    }

}