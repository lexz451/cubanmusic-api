package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.services.ImagesService
import info.cubanmusic.cubanmusicapi.services.JobTitleService
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Gender
import info.cubanmusic.cubanmusicapi.model.Person
import info.cubanmusic.cubanmusicapi.services.*
import info.cubanmusic.cubanmusicapi.dto.ArtistDTO
import info.cubanmusic.cubanmusicapi.dto.LocationDTO
import info.cubanmusic.cubanmusicapi.dto.QuoteDTO
import info.cubanmusic.cubanmusicapi.model.Location
import info.cubanmusic.cubanmusicapi.model.Quote
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/persons")
class PersonController {

    private val logger = LoggerFactory.getLogger(PersonController::class.java)

    @Autowired
    lateinit var personService: PersonService

    @Autowired
    lateinit var locationService: LocationService

    @Autowired
    lateinit var jobTitleService: JobTitleService

    @Autowired
    lateinit var awardService: AwardService

    @Autowired
    lateinit var albumService: AlbumService

    @Autowired
    lateinit var countryService: CountryService

    @Autowired
    lateinit var organizationService: OrganizationService

    @Autowired
    lateinit var genreService: GenreService

    @Autowired
    lateinit var instrumentService: InstrumentService

    @Autowired
    lateinit var labelService: LabelService

    @Autowired
    lateinit var imagesService: ImagesService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val persons = personService.findAll()
        if (persons.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(persons.map { toResponse(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val person = personService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(toResponse(person), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody request: ArtistDTO): ResponseEntity<*> {
        val person = fromRequest(Person(), request)
        personService.save(person)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: ArtistDTO): ResponseEntity<*> {
        var person = personService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        person = fromRequest(person, request)
        personService.save(person)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> {
        personService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        personService.delete(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @Suppress("LocalVariableName")
    @PostMapping("/{id}/quote")
    fun addQuote(@PathVariable id: Long, @RequestBody req: QuoteDTO): ResponseEntity<*> {
        val person = personService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        val _quote = Quote().apply {
            source = req.source
            author = req.author
            quote = req.quote
            date = Utils.parseDate(req.date)
        }
        person.quotes.add(_quote)
        personService.save(person)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    private fun toResponse(person: Person): ArtistDTO {
        return ArtistDTO().apply {
            id = person.id
            name = person.name
            alias = person.alias
            additionalNames = person.additionalNames.toList()
            description = person.description
            birthDate = Utils.formatDate(person.birthDate)
            deathDate = Utils.formatDate(person.deathDate)
            birthPlace = LocationDTO().apply {
                id = person.birthPlace?.id
                city = person.birthPlace?.city
                state = person.birthPlace?.state
                country = person.birthPlace?.country?.id
            }
            deathPlace = LocationDTO().apply {
                id = person.deathPlace?.id
                city = person.deathPlace?.city
                state = person.deathPlace?.state
                country = person.deathPlace?.country?.id
            }
            residencePlace = LocationDTO().apply {
                id = person.residencePlace?.id
                city = person.residencePlace?.city
                state = person.residencePlace?.state
                country = person.residencePlace?.country?.id
            }
            gender = person.gender.name
            jobTitle = person.jobTitle?.id
            jobRoles = person.jobRoles.toList()
            relatedTo = person.relatedTo?.id
            awards = person.awards.map { it.id!! }
            albums = person.albums.map { it.id!! }
            collaborations = person.collaborations.map { it.id!! }
            country = person.country?.id
            nationality = person.nationality
            activeSince = person.activeSince
            activeUntil = person.activeUntil
            affiliation = person.affiliation?.id
            genres = person.genres.map { it.id!! }
            instruments = person.instruments.map { it.id!! }
            studyAt = person.studiedAt?.id
            labels = person.labels.map { it.id!! }
            email = person.email
            website = person.website
            isniCode = person.isniCode
            spotify = person.spotify
            appleMusic = person.appleMusic
            youtube = person.youtube
            viberate = person.viberate
            soundCloud = person.soundCloud
            deezer = person.deezer
            instagram = person.instagram
            facebook = person.facebook
            twitter = person.twitter
            tiktok = person.tiktok
            libOfCongress = person.libOfCongress
            quotes = person.quotes.toList()
            images = person.images.map { it.id!! }
        }
    }

    private fun fromRequest(person: Person, request: ArtistDTO): Person {
        person.name = request.name ?: ""
        person.alias = request.alias ?: ""
        person.additionalNames = request.additionalNames.toMutableSet()
        person.description = request.description ?: ""
        person.birthDate = Utils.parseDate(request.birthDate)
        person.deathDate = Utils.parseDate(request.deathDate)
        person.birthPlace = Location().apply {
            id = request.birthPlace?.id
            request.birthPlace?.city?.let {
                city = it
            }
            request.birthPlace?.state?.let {
                state = it
            }
            request.birthPlace?.country?.let {
                country = countryService.findById(it)
            }
        }
        person.deathPlace = Location().apply {
            id = request.deathPlace?.id
            request.deathPlace?.city?.let {
                city = it
            }
            request.deathPlace?.state?.let {
                state = it
            }
            request.deathPlace?.country?.let {
                country = countryService.findById(it)
            }
        }
        person.residencePlace = Location().apply {
            id = request.residencePlace?.id
            request.residencePlace?.city?.let {
                city = it
            }
            request.residencePlace?.state?.let {
                state = it
            }
            request.residencePlace?.country?.let {
                country = countryService.findById(it)
            }
        }
        person.gender = Gender.valueOf(request.gender)
        request.jobTitle?.let { person.jobTitle = jobTitleService.findById(it) }
        person.jobRoles = request.jobRoles.toMutableSet()
        request.relatedTo?.let { person.relatedTo = personService.findById(it) }
        if (request.awards.isNotEmpty()) {
            person.awards = awardService.findAllByIds(request.awards)
        }
        if (request.albums.isNotEmpty()) {
            person.albums = albumService.findAllByIds(request.albums)
        }
        if (request.collaborations.isNotEmpty()) {
            person.collaborations = albumService.findAllByIds(request.collaborations)
        }
        request.country?.let { person.country = countryService.findById(it) }
        person.nationality = request.nationality
        person.activeSince = request.activeSince
        person.activeUntil = request.activeUntil
        request.affiliation?.let { person.affiliation = organizationService.findById(it) }
        if (request.genres.isNotEmpty()) {
            person.genres = genreService.findAllByIds(request.genres)
        }
        if (request.instruments.isNotEmpty()) {
            person.instruments = instrumentService.findAllByIds(request.instruments)
        }
        request.studyAt?.let { person.studiedAt = organizationService.findById(it) }
        if (request.labels.isNotEmpty()) {
            person.labels = labelService.findAllByIds(request.labels)
        }
        person.email = request.email
        person.website = request.website
        person.isniCode = request.isniCode
        person.spotify = request.spotify
        person.appleMusic = request.appleMusic
        person.youtube = request.youtube
        person.viberate = request.viberate
        person.soundCloud = request.soundCloud
        person.deezer = request.deezer
        person.instagram = request.instagram
        person.facebook = request.facebook
        person.twitter = request.twitter
        person.tiktok = request.tiktok
        person.libOfCongress = request.libOfCongress
        person.quotes = request.quotes.toMutableSet()

        person.images.clear()
        person.images.addAll(imagesService.findAllByIds(request.images))

        return person
    }

}
