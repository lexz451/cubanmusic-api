package info.cubanmusic.cubanmusicapi.database

import info.cubanmusic.cubanmusicapi.helper.CSVHelper
import info.cubanmusic.cubanmusicapi.services.CountryService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.io.FileInputStream

@Component
class DatabaseSeeder : ApplicationRunner {

    @Autowired
    private lateinit var countryService: CountryService

    private val logger = LoggerFactory.getLogger(DatabaseSeeder::class.java)

    override fun run(args: ApplicationArguments?) {
        logger.info("Start seeding database...!")

        logger.info("Seeding countries...!")
        if (countryService.findAll().isEmpty()) {
            try {
                val file = ResourceUtils.getFile("classpath:static/countries.csv")
                val inputStream = FileInputStream(file)
                val countries = CSVHelper.csvToCountries(inputStream)
                countryService.saveAll(countries)
            } catch (e: Exception) {
                throw RuntimeException("An error occurred when reading csv file.")
            }
        } else {
            logger.info("Database already have countries. Skipping.")
        }
        logger.info("Finished seeding database. Enjoy!")
    }
}