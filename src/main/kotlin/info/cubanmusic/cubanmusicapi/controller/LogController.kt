package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.*
import info.cubanmusic.cubanmusicapi.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/logs")
class LogController {

    @Autowired
    lateinit var personRepository: PersonRepository
    @Autowired
    lateinit var groupRepository: GroupRepository
    @Autowired
    lateinit var albumRepository: AlbumRepository
    @Autowired
    lateinit var awardRepository: AwardRepository
    @Autowired
    lateinit var organizationRepository: OrganizationRepository
    @Autowired
    lateinit var recordLabelRepository: RecordLabelRepository
    @Autowired
    lateinit var venueRepository: VenueRepository
    @Autowired
    lateinit var genreRepository: GenreRepository
    @Autowired
    lateinit var jobTitleRepository: JobTitleRepository
    @Autowired
    lateinit var instrumentRepository: InstrumentRepository
    @Autowired
    lateinit var locationRepository: LocationRepository
    @Autowired
    lateinit var imageRepository: LocationRepository
    @Autowired
    lateinit var userRepository: UserRepository

    class Log(
        val type: String? = null,
        var id: Long? = null,
        var name: String? = null,
        var createdAt: LocalDateTime? = null,
        var updatedAt: LocalDateTime? = null,
        var createdBy: User? = null,
        var updatedBy: User? = null
    )

    @GetMapping("/latest")
    fun getLogs(): ResponseEntity<*> {
        val logs = getLatestChangeLog();
        return ResponseEntity(logs, HttpStatus.OK);
    }

    fun getLatestChangeLog(): List<Log?> {
        val users = userRepository.findAll()
        val persons = personRepository.findAll().take(10)
        val groups = groupRepository.findAll().take(10)
        val albums = albumRepository.findAll().take(10)
        val awards = awardRepository.findAll().take(10)
        val orgs = organizationRepository.findAll().take(10)
        val records = recordLabelRepository.findAll().take(10)
        val venue = venueRepository.findAll().take(10)
        val genre = genreRepository.findAll().take(10)
        val jobTitle = jobTitleRepository.findAll().take(10)
        val instrument = instrumentRepository.findAll().take(10)
        val locations = locationRepository.findAll().take(10)
        val images = imageRepository.findAll().take(10)
        val logs = concat(users, persons, groups, albums, awards, orgs, records, venue, genre, jobTitle, instrument, locations, images)
            .map {
                val log = Log(it::class.java.simpleName)
                log.id = it.id
                log.createdBy = it.createdBy.orElseGet { null }
                log.updatedBy = it.lastModifiedBy.orElseGet { null }
                log.createdAt = it.createdDate.orElseGet { null }
                log.updatedAt = it.lastModifiedDate.orElseGet { null }
                log.name = when(it) {
                    is User -> it.name
                    is Album -> it.title
                    is Award -> it.title
                    is Person -> it.name
                    is Group -> it.name
                    is Organization -> it.name
                    is RecordLabel -> it.name
                    is Venue -> it.name
                    is Genre -> it.name
                    is JobTitle -> it.name
                    is Instrument -> it.name
                    is Location -> "${it.city}:${it.state}:${it.country?.name}"
                    is Image -> it.filename
                    else -> null
                }
                log
            }
        return logs
    }

    fun <T> concat(vararg list: List<T>): List<T> {
        return listOf(*list).flatten()
    }
}