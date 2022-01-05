package info.cubanmusic.cubanmusicapi.database

import info.cubanmusic.cubanmusicapi.model.*
import info.cubanmusic.cubanmusicapi.repository.CountryRepository
import info.cubanmusic.cubanmusicapi.repository.UserRepository
import info.cubanmusic.cubanmusicapi.services.*

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DatabaseSeeder : ApplicationRunner {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Value("\${security.adminEmail}")
    private lateinit var adminEmail: String

    @Value("\${security.adminPassword}")
    private lateinit var adminPassword: String

    private val logger = LoggerFactory.getLogger(DatabaseSeeder::class.java)

    override fun run(args: ApplicationArguments?) {
        logger.info("Start seeding database...!")

        logger.info("Seeding admin account...!")
        if (userRepository.findByEmail(adminEmail) == null) {
            try {
                val admin = User()
                admin.email = adminEmail
                admin.name = "Administrador"
                admin.enabled = true
                admin.password = passwordEncoder.encode(adminPassword)
                admin.role = Role.SUPER_ADMIN
                userRepository.save(admin)
                logger.info("Admin user created successfully.")
            } catch (e: Exception) {
                logger.error("An error occurred while creating admin user.", e)
                throw RuntimeException("An error occurred while creating admin user")
            }
        } else {
            logger.info("Admin user already exists. Skipping.")
        }

        logger.info("Seeding default country...")
        if (countryRepository.findByIso2Code("CU") == null) {
            val defaultCountry = Country().apply {
                name = "Cuba"
                iso2Code = "CU"
                phoneCode = "+53"
            }
            countryRepository.save(defaultCountry)
        }


        logger.info("Finished seeding database. Enjoy!")
    }
}