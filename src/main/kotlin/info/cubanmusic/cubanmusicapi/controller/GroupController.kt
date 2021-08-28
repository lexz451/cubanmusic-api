package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Group
import info.cubanmusic.cubanmusicapi.services.*
import info.cubanmusic.cubanmusicapi.dto.ArtistDTO
import info.cubanmusic.cubanmusicapi.dto.LocationDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Gender
import info.cubanmusic.cubanmusicapi.model.Location
import info.cubanmusic.cubanmusicapi.model.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/groups")
class GroupController {

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
    lateinit var personService: PersonService
    @Autowired
    lateinit var labelService: LabelService
    @Autowired
    lateinit var groupService: GroupService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val groups = groupService.findAll()
        if (groups.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(groups, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val group = groupService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(toResponse(group), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody request: ArtistDTO): ResponseEntity<*> {
        val group = fromRequest(Group(), request)
        groupService.save(group)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: ArtistDTO): ResponseEntity<*> {
        var group = groupService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        group = fromRequest(group, request)
        groupService.save(group)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    private fun toResponse(group: Group): ArtistDTO {
        return ArtistDTO().apply {
            id = group.id
            name = group.name
            alias = group.alias
            additionalNames = group.additionalNames.toList()
            description = group.description
            awards = group.awards.map { it.id!! }
            albums = group.albums.map { it.id!! }
            collaborations = group.collaborations.map { it.id!! }
            members = group.members.map { it.id!! }
            country = group.country?.id
            activeSince = group.activeSince
            activeUntil = group.activeUntil
            affiliation = group.affiliation?.id
            genres = group.genres.map { it.id!! }
            instruments = group.instruments.map { it.id!! }
            studyAt = group.studiedAt?.id
            labels = group.labels.map { it.id!! }
            email = group.email
            website = group.website
            isniCode = group.isniCode
            spotify = group.spotify
            appleMusic = group.appleMusic
            youtube = group.youtube
            viberate = group.viberate
            soundCloud = group.soundCloud
            deezer = group.deezer
            instagram = group.instagram
            facebook = group.facebook
            twitter = group.twitter
            tiktok = group.tiktok
            libOfCongress = group.libOfCongress
            quotes = group.quotes.toList()
        }
    }

    private fun fromRequest(group: Group, request: ArtistDTO): Group {
        group.name = request.name ?: ""
        group.alias = request.alias ?: ""
        group.additionalNames = request.additionalNames.toMutableSet()
        group.description = request.description ?: ""
        if (request.awards.isNotEmpty()) {
            group.awards = awardService.findAllByIds(request.awards)
        }
        if (request.albums.isNotEmpty()) {
            group.albums = albumService.findAllByIds(request.albums)
        }
        if (request.collaborations.isNotEmpty()) {
            group.collaborations = albumService.findAllByIds(request.collaborations)
        }
        if (request.members.isNotEmpty()) {
            group.members = personService.findAllByIds(request.members)
        }
        request.country?.let { group.country = countryService.findById(it) }
        group.activeSince = request.activeSince
        group.activeUntil = request.activeUntil
        request.affiliation?.let { group.affiliation = organizationService.findById(it) }
        if (request.genres.isNotEmpty()) {
            group.genres = genreService.findAllByIds(request.genres)
        }
        if (request.instruments.isNotEmpty()) {
            group.instruments = instrumentService.findAllByIds(request.instruments)
        }
        request.studyAt?.let { group.studiedAt = organizationService.findById(it) }
        if (request.labels.isNotEmpty()) {
            group.labels = labelService.findAllByIds(request.labels)
        }
        group.email = request.email
        group.website = request.website
        group.isniCode = request.isniCode
        group.spotify = request.spotify
        group.appleMusic = request.appleMusic
        group.youtube = request.youtube
        group.viberate = request.viberate
        group.soundCloud = request.soundCloud
        group.deezer = request.deezer
        group.instagram = request.instagram
        group.facebook = request.facebook
        group.twitter = request.twitter
        group.tiktok = request.tiktok
        group.libOfCongress = request.libOfCongress
        group.quotes = request.quotes.toMutableSet()
        return group
    }
}