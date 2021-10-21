package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.PersonDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Gender
import info.cubanmusic.cubanmusicapi.model.Person
import info.cubanmusic.cubanmusicapi.repository.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/persons")
class PersonController {

    private val logger = LoggerFactory.getLogger(PersonController::class.java)

    @Autowired
    private lateinit var locationRepository: LocationRepository
    @Autowired
    private lateinit var countryRepository: CountryRepository
    @Autowired
    private lateinit var organizationRepository: OrganizationRepository
    @Autowired
    private lateinit var genreRepository: GenreRepository
    @Autowired
    private lateinit var awardRepository: AwardRepository
    @Autowired
    private lateinit var instrumentRepository: InstrumentRepository
    @Autowired
    private lateinit var schoolRepository: SchoolRepository
    @Autowired
    private lateinit var jobTitleRepository: JobTitleRepository
    @Autowired
    private lateinit var personRepository: PersonRepository
    @Autowired
    private lateinit var albumRepository: AlbumRepository
    @Autowired
    private lateinit var labelRepository: RecordLabelRepository;
    @Autowired
    private lateinit var imagesRepository: ImageRepository;

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val persons = personRepository.findAll()
        if (persons.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(persons.map { fromModel(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val person = personRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(fromModel(person), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody request: PersonDTO): ResponseEntity<*> {
        var person = toModel(request, Person())
        person = personRepository.save(person)
        return ResponseEntity(person.id, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: PersonDTO): ResponseEntity<*> {
        var person = personRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        person = toModel(request, person)
        personRepository.save(person)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> {
        personRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        personRepository.deleteById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    fun fromModel(artist: Person): PersonDTO {
        return PersonDTO().apply {
            id = artist.id
            name = artist.name
            additionalNames = artist.additionalNames.toList()
            alias = artist.alias
            biography = artist.biography

            birthDate = Utils.formatDate(artist.birthDate)
            deathDate = Utils.formatDate(artist.deathDate)
            birthPlace = artist.birthPlace?.id
            deathPlace = artist.deathPlace?.id
            residencePlace = artist.residencePlace?.id
            gender = artist.gender.name
            jobTitle = artist.jobTitle?.id
            jobRoles = artist.jobRoles.toList()
            relatedTo = artist.relatedTo?.id
            label = artist.recordLabel?.id

            email = artist.email
            website = artist.website
            activeSince = artist.activeSince
            activeUntil = artist.activeUntil
            isniCode = artist.isniCode
            spotify = artist.spotify
            appleMusic = artist.appleMusic
            soundCloud = artist.soundCloud
            deezer = artist.deezer
            youtube = artist.youtube
            instagram = artist.instagram
            viberate = artist.viberate
            facebook = artist.facebook
            twitter = artist.twitter
            tiktok = artist.tiktok
            libOfCongress = artist.libOfCongress
            nationality = artist.nationality
            country = artist.country?.id
            affiliation = artist.affiliation?.id
            genres = artist.genres.map { it.id!! }
            awards = artist.awards.map { it.id!! }
            instruments = artist.instruments.map { it.id!! }
            studyAt = artist.studyAt?.id
            albums = artist.albums.map { it.id!! }
        }
    }

    fun toModel(artistDTO: PersonDTO, artist: Person): Person {
        return artist.apply {
            name = artistDTO.name
            additionalNames = artistDTO.additionalNames.toMutableSet()
            alias = artistDTO.alias
            biography = artistDTO.biography

            birthDate = Utils.parseDate(artistDTO.birthDate)
            deathDate = Utils.parseDate(artistDTO.deathDate)
            birthPlace = locationRepository.findByIdOrNull(artistDTO.birthPlace ?: -1)
            deathPlace = locationRepository.findByIdOrNull(artistDTO.deathPlace ?: -1)
            residencePlace = locationRepository.findByIdOrNull(artistDTO.residencePlace ?: -1)
            gender = Gender.valueOf(artistDTO.gender)
            jobTitle = jobTitleRepository.findByIdOrNull(artistDTO.jobTitle ?: -1)
            jobRoles = artistDTO.jobRoles.toMutableSet()
            recordLabel = labelRepository.findByIdOrNull(artistDTO.label ?: -1)

            relatedTo = personRepository.findByIdOrNull(artistDTO.relatedTo ?: -1)

            email = artistDTO.email
            website = artistDTO.website
            activeSince = artistDTO.activeSince
            activeUntil = artistDTO.activeUntil
            isniCode = artistDTO.isniCode
            spotify = artistDTO.spotify
            appleMusic = artistDTO.appleMusic
            soundCloud = artistDTO.soundCloud
            deezer = artistDTO.deezer
            youtube = artistDTO.youtube
            instagram = artistDTO.instagram
            viberate = artistDTO.viberate
            facebook = artistDTO.facebook
            twitter = artistDTO.twitter
            tiktok = artistDTO.tiktok
            libOfCongress = artistDTO.libOfCongress
            nationality = artistDTO.nationality

            artistDTO.country?.let {
                country = countryRepository.findByIdOrNull(it)
            }

            affiliation = organizationRepository.findByIdOrNull(artistDTO.affiliation ?: -1)


            genres = genreRepository.findAllById(artistDTO.genres)
            awards = awardRepository.findAllById(artistDTO.awards)
            instruments = instrumentRepository.findAllById(artistDTO.instruments)

            studyAt = schoolRepository.findByIdOrNull(artistDTO.studyAt ?: -1)

            albums = albumRepository.findAllById(artistDTO.albums)
        }
    }
}
