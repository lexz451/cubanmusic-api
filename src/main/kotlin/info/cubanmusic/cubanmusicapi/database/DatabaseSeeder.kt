package info.cubanmusic.cubanmusicapi.database

import info.cubanmusic.cubanmusicapi.helper.CSVHelper
import info.cubanmusic.cubanmusicapi.model.Role
import info.cubanmusic.cubanmusicapi.model.User
import info.cubanmusic.cubanmusicapi.repository.UserRepository
import info.cubanmusic.cubanmusicapi.services.CountryService
import info.cubanmusic.cubanmusicapi.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.io.FileInputStream

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
                logger.error("An error occurred when reading csv file.", e)
                throw RuntimeException("An error occurred when reading csv file.")
            }
        } else {
            logger.info("Database already have countries. Skipping.")
        }

        logger.info("Seeding admin account...!")
        if (userService.findByEmail(adminEmail) == null) {
            try {
                val admin = User()
                admin.email = adminEmail
                admin.name = "Administrador"
                admin.enabled = true
                admin.password = passwordEncoder.encode(adminPassword)
                admin.role = Role.SUPER_ADMIN
                userService.saveUser(admin)
                logger.info("Admin user created successfully.")
            } catch (e: Exception) {
                logger.error("An error occurred while creating admin user.", e)
                throw RuntimeException("An error occurred while creating admin user")
            }
        } else {
            logger.info("Admin user already exists. Skipping.")
        }

        logger.info("Finished seeding database. Enjoy!")
    }
}