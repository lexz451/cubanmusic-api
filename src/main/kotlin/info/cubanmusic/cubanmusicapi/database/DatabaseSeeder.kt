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
import org.springframework.util.ResourceUtils
import java.io.FileInputStream
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Component
class DatabaseSeeder : ApplicationRunner {

    @Autowired
    private lateinit var countryService: CountryService
    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var jobTitleService: JobTitleService
    @Autowired
    private lateinit var personService: PersonService
    @Autowired
    private lateinit var groupService: GroupService
    @Autowired
    private lateinit var awardService: AwardService
    @Autowired
    private lateinit var organizationService: OrganizationService
    @Autowired
    private lateinit var instrumentService: InstrumentService
    @Autowired
    private lateinit var genreService: GenreService
    @Autowired
    private lateinit var labelService: LabelService
    @Autowired
    private lateinit var albumService: AlbumService
    @Autowired
    private lateinit var locationService: LocationService

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
        logger.info("Seeding organizations...")
        if (organizationService.findAll().isEmpty()) {
            val org = Organization().apply {
                country = countryService.findAll().find { c -> c.iso2Code?.equals("CU") ?: false }
                email = "org@email.com"
                name = "Some ORG"
                description = "Some Desc"
                address = "Street #333"
                phone = Phone().apply {
                    code = "+53"
                    number = "53314203"
                }
                website = "www.somewebsite.com"
            }

            organizationService.save(org)
        }

        logger.info("Seeding awards...")
        if (awardService.findAll().isEmpty()) {
            (1..3).forEach { n ->
                val a = Award().apply {
                    title = "Award $n"
                    country = countryService.findAll()[n]
                    description = "Some award"
                    categories = setOf("Category #1", "Category #2")
                    grantedBy = organizationService.findAll().first()
                }
                awardService.save(a)
            }
        }

        logger.info("Seeding instruments...")
        if (instrumentService.findAll().isEmpty()) {
            for (i in (1..10)) {
                val instrument = Instrument().apply {
                    name = "Instrument $i"
                    description = "Some instrument description..."
                }
                instrumentService.save(instrument)
            }
        }

        logger.info("Seeding genres...")
        if (genreService.findAll().isEmpty()) {
            for (i in (1..10)) {
                val genre = Genre().apply {
                    name = "Genre $i"
                    description = "Some genre description"
                }
                genreService.save(genre)
            }
        }

        logger.info("Seeding record labels")
        if (labelService.findAll().isEmpty()) {
            for (i in (1..10)) {
                val recordLabel = RecordLabel().apply {
                    ipiCode = Math.random().plus(i).toInt().toString()
                    isniCode = Math.random().plus(i).toInt().toString()
                    country = countryService.findAll().find { c -> c.iso2Code?.equals("CU") ?: false }
                    email = "label${i}@email.com"
                    name = "Label #$i"
                    description = "Some record label description"
                    address = "Street #333"
                    phone = Phone().apply {
                        code = "+53"
                        number = "53314203"
                    }
                    website = "www.recordlabelwebsite.com"
                }
                labelService.save(recordLabel)
            }
        }

        logger.info("Seeding job titles")
        if (jobTitleService.findAll().isEmpty()) {
            for (i in (1..10)) {
                val job = JobTitle().apply {
                    title = "Job $i"
                    description = "Some job title description"
                }
                jobTitleService.save(job)
            }
        }

        logger.info("Seeding some artists")
        if (personService.findAll().isEmpty()) {
            val havana = Location().apply {
                city = "La Habana"
                state = "La Habana"
                country = countryService.findAll().find { c -> c.iso2Code == "CU" }
            }
            val paris = Location().apply {
                city = "Paris"
                state = "Paris"
                country = countryService.findAll().find { c -> c.iso2Code == "FR" }
            }
            locationService.save(havana)
            locationService.save(paris)
            for (i in (1..100)) {
                val artist = Person().apply {
                    name = "Artist $i"
                    alias = "Artist alias"
                    additionalNames = setOf("Additional name", "Additional name", "Additional name")
                    birthDate = Date()
                    deathDate = Date()
                    birthPlace = locationService.findAll().first()
                    residencePlace = locationService.findAll().last()
                    gender = Gender.OTHER
                    jobTitle = jobTitleService.findById(i.toLong()) ?: jobTitleService.findAll().first()
                    jobRoles = setOf("Some rol", "Some other role", "Some other role x", "Some other role y")
                    relatedTo = personService.findAll().lastOrNull()
                    country = countryService.findAll().find { c -> c.iso2Code == "CU" }
                    activeSince = 1991
                    affiliation = organizationService.findAll().first()
                    genres = genreService.findAll()
                    instruments = instrumentService.findAll()
                    studiedAt = organizationService.findAll().first()
                    description = "Some artist bio"
                    email = "art@gmail.com"
                    website = "www.artists.com"
                    isniCode = "00000000000001$i"
                    spotify = "spotify.com/artist"
                    appleMusic = "applemusic.com/artist"
                    youtube = "youtube.com/artist"
                    instagram = "instagram.com/xxxx"
                    quotes = setOf(
                        Quote().apply {
                            source = "AM.PM"
                            quote = "Amazing artist"
                            date = Date()
                        },
                        Quote().apply {
                            source = "AM.PM"
                            quote = "Amazing artist again"
                            date = Date()
                        }
                    )
                    awards = mutableListOf(awardService.findAll().first())
                    albums = (1..3).map {
                        Album().apply {
                            title = "$name - Album $i"
                            description = "Some album description that is probably too short"
                            releasedOn = Date()
                        }
                    }.toMutableList()
                }
                personService.save(artist)
            }
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
                userService.save(admin)
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