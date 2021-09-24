package info.cubanmusic.cubanmusicapi.database

import info.cubanmusic.cubanmusicapi.helper.CSVHelper
import info.cubanmusic.cubanmusicapi.model.*
import info.cubanmusic.cubanmusicapi.services.*

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.core.io.ClassPathResource

@Component
class DatabaseSeeder : ApplicationRunner {

    @Autowired
    private lateinit var countryService: CountryService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Value("\${security.adminEmail}")
    private lateinit var adminEmail: String

    @Value("\${security.adminPassword}")
    private lateinit var adminPassword: String

    private val log = LoggerFactory.getLogger(DatabaseSeeder::class.java)

    override fun run(args: ApplicationArguments?) {
        log.info("Start seeding database...!")

        log.info("Seeding countries...!")
        if (countryService.findAll().isEmpty()) {
            try {
                val inputStream = ClassPathResource("static/countries.csv").inputStream
                val countries = CSVHelper.csvToCountries(inputStream)
                countryService.saveAll(countries)
            } catch (e: Exception) {
                log.error("An error occurred when reading csv file.", e)
                throw RuntimeException("An error occurred when reading csv file.")
            }
        } else {
            log.info("Database already have countries. Skipping.")
        }

        log.info("Seeding admin account...!")
        if (userService.findByEmail(adminEmail) == null) {
            try {
                val admin = User()
                admin.email = adminEmail
                admin.name = "Administrador"
                admin.enabled = true
                admin.password = passwordEncoder.encode(adminPassword)
                admin.role = Role.SUPER_ADMIN
                userService.save(admin)
                log.info("Admin user created successfully.")
            } catch (e: Exception) {
                log.error("An error occurred while creating admin user.", e)
                throw RuntimeException("An error occurred while creating admin user")
            }
        } else {
            log.info("Admin user already exists. Skipping.")
        }
        log.info("Finished seeding database. Enjoy!")
    }
}