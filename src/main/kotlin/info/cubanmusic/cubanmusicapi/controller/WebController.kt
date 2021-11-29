package info.cubanmusic.cubanmusicapi.controller


import info.cubanmusic.cubanmusicapi.dto.ArticleDTO
import info.cubanmusic.cubanmusicapi.dto.OrganizationDTO
import info.cubanmusic.cubanmusicapi.dto.QuoteDTO
import info.cubanmusic.cubanmusicapi.dto.VenueDTO
import info.cubanmusic.cubanmusicapi.dto.public.AlbumDTO
import org.springframework.security.access.annotation.Secured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import info.cubanmusic.cubanmusicapi.dto.public.ArtistDTO
import info.cubanmusic.cubanmusicapi.dto.public.ImageDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.*
import info.cubanmusic.cubanmusicapi.repository.*
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/public")
class WebController {

    @Autowired
    lateinit var artistRepository: ArtistRepository;
    @Autowired
    lateinit var personRepository: PersonRepository;
    @Autowired
    lateinit var imagesRepository: ImageRepository;
    @Autowired
    lateinit var albumsRepository: AlbumRepository;
    @Autowired
    lateinit var groupRepository: GroupRepository
    @Autowired
    lateinit var venuesRepository: VenueRepository
    @Autowired
    lateinit var organizationRepository: OrganizationRepository
    @Autowired
    lateinit var recordLabelRepository: RecordLabelRepository
    @Autowired
    lateinit var quoteReferenceRepository: QuoteReferenceRepository
    @Autowired
    lateinit var articleReferenceRepository: ArticleReferenceRepository

    val logger = LoggerFactory.getLogger(WebController::class.java)

    @GetMapping("/artists")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    fun getArtists(): ResponseEntity<*> {
        val artists = this.personRepository.findAll();        
        return ResponseEntity(artists.map(mapArtist()), HttpStatus.OK);
    }

    @GetMapping("/groups")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    fun getGroups(): ResponseEntity<*> {
        val groups = this.groupRepository.findAll()
        return ResponseEntity(groups.map(mapGroup()), HttpStatus.OK)
    }

    @GetMapping("/venues")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    fun getVenues(): ResponseEntity<*> {
        val venues = this.venuesRepository.findAll()
        return ResponseEntity(venues.map(mapVenue()), HttpStatus.OK)
    }

    @GetMapping("/orgs")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    fun getOrgs(): ResponseEntity<*> {
        val orgs = this.organizationRepository.findAll()
        return ResponseEntity(orgs.map(mapOrg()), HttpStatus.OK)
    }

    @GetMapping("/artists/{id}")
    @Transactional(readOnly = true)
    fun getArtist(@PathVariable id: Long): ResponseEntity<*> {
        return personRepository.findById(id).map {
            val artistDTO = ArtistDTO()
            artistDTO.id = it.id
            artistDTO.name = it.name
            artistDTO.occupation = it.jobTitle?.name
            artistDTO.roles = it.jobRoles.toList()
            artistDTO.birthDate = Utils.formatDate(it.birthDate)
            artistDTO.nationality = it.nationality
            artistDTO.website = it.website
            artistDTO.spotify = it.spotify;
            artistDTO.appleMusic = it.appleMusic;
            artistDTO.soundCloud = it.soundCloud;
            artistDTO.deezer = it.deezer;
            artistDTO.youtube = it.youtube;
            artistDTO.instagram = it.instagram;
            artistDTO.viberate = it.viberate;
            artistDTO.facebook = it.facebook;
            artistDTO.twitter = it.twitter;
            artistDTO.tiktok = it.tiktok;
            artistDTO.reverbNation = it.reverbNation;

            it.images.firstOrNull()?.let { image ->
                artistDTO.image = ImageDTO().apply {
                    this.data = Utils.decompressBytes(image.filedata)
                    this.type = image.filetype
                }
            }

            artistDTO.residence = it.residencePlace?.let { residence ->
                val join = mutableSetOf<String>()
                residence.city?.let { join.add(residence.city!!) }
                residence.state?.let { join.add(residence.state!!) }
                residence.country?.let { join.add(residence.country!!.name) }
                join.joinToString(", ")
            }
            artistDTO.yearsActive = it.activeSince?.let { since ->
                "$since - ${ it.activeUntil ?: "-" }"
            }
            artistDTO.instruments = it.instruments.map { i -> i.name }
            artistDTO.genres = it.genres.map { g -> g.name }
            artistDTO.albums = it.albums.map {
                AlbumDTO().apply {
                    this.id = it.id
                    this.name = it.title
                    this.image = it.image
                    this.releasedDate = Utils.formatDate(it.releaseDate)
                }
            }
            artistDTO.quotes = it.quoteReferences.map { QuoteDTO().apply {
                this.id = it.id
                this.author = it.author
                this.date = Utils.formatDate(it.date)
                this.quote = it.quote
                this.source = it.source
            } }
            artistDTO.articles = it.articleReferences.map {
                ArticleDTO().apply {
                    this.id = it.id
                    this.source = it.source
                    this.url = it.url
                    this.author = it.author
                    this.title = it.title
                }
            }
            ResponseEntity(artistDTO, HttpStatus.OK)
        }.orElseGet { ResponseEntity(HttpStatus.NOT_FOUND) }
    }

    /*@GetMapping("/artists/{id}")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    fun getArtist(@PathVariable id: Long):  ResponseEntity<*> {

        if (artist.isEmpty) return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)

        return ResponseEntity(ArtistDTO().apply { 
            this.id = artist.id!!
            name = artist.name
            roles = artist.jobRoles.toList();
            occupation = artist.jobTitle?.name;
            bio = artist.biography;
            birthDate = Utils.formatDate(artist.birthDate);
            nationality = artist.nationality;
            artist.residencePlace?.let {
                val _data = mutableSetOf<String>();
                it.city?.let { _data.add(it) }
                it.state?.let { _data.add(it) }
                it.country?.let { _data.add(it.name) };
                residence = _data.joinToString(", ");
            }
            yearsActive = "${artist.activeSince} - ${artist.activeUntil}";
            instruments = artist.instruments.map { it.name }
            genres = artist.genres.map { it.name }
            school = artist.studyAt?.name;
            isni = artist.isniCode;
            //ipiCode = artist;
            website = artist.website;
            
            spotify = artist.spotify;
            appleMusic = artist.appleMusic;
            soundCloud = artist.soundCloud;
            deezer = artist.deezer;
            youtube = artist.youtube;
            instagram = artist.instagram;
            viberate = artist.viberate;
            facebook = artist.facebook;
            twitter = artist.twitter;
            tiktok = artist.tiktok;
            reverbNation = artist.reverbNation;

            val artistImages = imagesRepository.findByArtistId(artist.id!!);
            if (artistImages.isNotEmpty()) {
                image = ImageDTO().apply { 
                    data = Utils.decompressBytes(artistImages.first().filedata)
                    type = artistImages.first().filetype
                }
            }

            val artistAlbums = artist.albums.map { a -> 
                AlbumDTO().apply {
                    this.id = a.id
                    this.name = a.title
                    this.releasedDate = a.copyrightYear.toString()
                    this.image = a.image
                }
            }

            albums = artistAlbums

            quotes = artist.quoteReferences.map {
                QuoteDTO().apply {
                    this.id = it.id
                    this.quote = it.quote
                    this.author = it.author
                    this.date = Utils.formatDate(it.date)
                }
            }

            articles = artist.articleReferences.map {
                ArticleDTO().apply {
                    this.id = it.id
                    this.date = Utils.formatDate(it.date)
                    this.author = it.author
                    this.title = it.title
                    this.source = it.source
                    this.url = it.url
                }
            }

         }, HttpStatus.OK);
    }*/

    @GetMapping("/search")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    fun search(@RequestParam keyword: String?): ResponseEntity<*> {
        logger.info("Trigger search with query: $keyword")
        if (keyword == null) return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)

        val artists = personRepository.searchByName(keyword).map {
            val image = it[3]
            if (image != null) {
                val base64 = Utils.decompressBytes(image as ByteArray)
                if (base64 != null) {
                    it[3] = base64
                }
            }
            it
        }.toTypedArray()

        val groups = groupRepository.searchByName(keyword).map {
            val image = it[3]
            if (image != null) {
                val base64 = Utils.decompressBytes(image as ByteArray)
                if (base64 != null) {
                    it[3] = base64
                }
            }
            it
        }.toTypedArray()

        val venues = venuesRepository.searchByName(keyword).toTypedArray()
        val organizations = organizationRepository.searchByName(keyword).toTypedArray()
        val recordLabels = recordLabelRepository.searchByName(keyword).toTypedArray()

        val results = arrayOf(
            *artists,
            *groups,
            *venues,
            *organizations,
            *recordLabels
        )

        return ResponseEntity(results, HttpStatus.OK)
    }

    fun mapArtist() = { artist: Artist ->
        ArtistDTO().apply { 
            id = artist.id
            name = artist.name
            val artistImages = imagesRepository.findByArtistId(artist.id!!)
            if (artistImages.isNotEmpty()) {
                image = ImageDTO().apply { 
                    data = Utils.decompressBytes(artistImages.first().filedata)
                    type = artistImages.first().filetype
                }
            }
        }
    }

    fun mapVenue() = { venue: Venue ->
        VenueDTO().apply {
            id = venue.id
            name = venue.name
            description = venue.description
            venueType = venue.venueType?.name
            image = venue.image
        }
    }

    fun mapOrg() = { org: Organization ->
        OrganizationDTO().apply {
            id = org.id
            name = org.name
            description = org.description
        }
    }

    fun mapGroup() = { group: Group ->
        ArtistDTO().apply {
            id = group.id
            name = group.name
            val artistImages = imagesRepository.findByArtistId(group.id!!)
            if (artistImages.isNotEmpty()) {
                image = ImageDTO().apply {
                    data = Utils.decompressBytes(artistImages.first().filedata)
                    type = artistImages.first().filetype
                }
            }
        }
    }
}