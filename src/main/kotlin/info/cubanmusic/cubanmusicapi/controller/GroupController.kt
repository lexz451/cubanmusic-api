package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Group
import info.cubanmusic.cubanmusicapi.services.*
import info.cubanmusic.cubanmusicapi.dto.ArtistDTO
import info.cubanmusic.cubanmusicapi.dto.GroupDTO
import info.cubanmusic.cubanmusicapi.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/groups")
class GroupController {

    @Autowired
    private lateinit var groupRepository: GroupRepository
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
    private lateinit var recordLabelRepository: RecordLabelRepository

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val groups = groupRepository.findAll()
        if (groups.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(groups.map { fromModel(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val group = groupRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(fromModel(group), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody request: GroupDTO): ResponseEntity<*> {
        var group = toModel(request, Group())
        group = groupRepository.save(group)
        return ResponseEntity(group.id, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: GroupDTO): ResponseEntity<*> {
        var group = groupRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        group = toModel(request, group)
        groupRepository.save(group)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> {
        groupRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        groupRepository.deleteById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    fun fromModel(artist: Group): GroupDTO {
        return GroupDTO().apply {
            id = artist.id
            name = artist.name
            additionalNames = artist.additionalNames.toList()
            alias = artist.alias
            biography = artist.biography
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
            members = artist.members.map { it.id!! }
            label = artist.recordLabel?.id
        }
    }

    fun toModel(artistDTO: GroupDTO, artist: Group): Group {
        return artist.apply {
            name = artistDTO.name
            additionalNames = artistDTO.additionalNames.toMutableSet()
            alias = artistDTO.alias
            biography = artistDTO.biography
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
            artistDTO.country?.let { 
                System.out.println(artistDTO.country)
                country = countryRepository.findByIdOrNull(it)
            }
            artistDTO.affiliation?.let {
                affiliation = organizationRepository.findByIdOrNull(it)
            }
            if (artistDTO.genres.isNotEmpty()) {
                genres = genreRepository.findAllById(artistDTO.genres)
            }
            if (artistDTO.awards.isNotEmpty()) {
                awards = awardRepository.findAllById(artistDTO.awards)
            }
            artistDTO.label?.let { 
                recordLabel = recordLabelRepository.findByIdOrNull(it)    
            }
            
        }
    }
}