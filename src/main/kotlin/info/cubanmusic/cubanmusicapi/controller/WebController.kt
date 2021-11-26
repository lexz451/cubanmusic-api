package info.cubanmusic.cubanmusicapi.controller


import info.cubanmusic.cubanmusicapi.dto.ArticleDTO
import info.cubanmusic.cubanmusicapi.dto.OrganizationDTO
import info.cubanmusic.cubanmusicapi.dto.QuoteDTO
import info.cubanmusic.cubanmusicapi.dto.VenueDTO
import org.springframework.security.access.annotation.Secured
import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import info.cubanmusic.cubanmusicapi.model.Artist
import info.cubanmusic.cubanmusicapi.dto.public.ArtistDTO
import info.cubanmusic.cubanmusicapi.dto.public.ImageDTO
import info.cubanmusic.cubanmusicapi.dto.public.AlbumDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Group
import info.cubanmusic.cubanmusicapi.model.Organization
import info.cubanmusic.cubanmusicapi.model.Venue
import info.cubanmusic.cubanmusicapi.repository.*
import kotlin.collections.emptyList
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

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
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    fun getArtist(@PathVariable id: Long):  ResponseEntity<*> {
        val artist = this.personRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
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
    }

    @PersistenceContext
    lateinit var em: EntityManager

    @PostMapping("/search")
    fun search(@RequestBody keyword: String?): ResponseEntity<*> {
        if (keyword == null) return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        val query = em.createNativeQuery(
                "SELECT 'Artist' OriginatingTable, id, name " +
                        "FROM contributor " +
                        "WHERE name LIKE '%$keyword%' AND dtype = 'Person' " +
                        "UNION ALL " +
                        "SELECT 'Album', id, title " +
                        "FROM albums " +
                        "WHERE title LIKE '%$keyword%' " +
                        "UNION ALL " +
                        "SELECT 'Group', id, name " +
                        "FROM contributor " +
                        "WHERE name LIKE '%$keyword%' AND dtype = 'Group' " +
                        "UNION ALL " +
                        "SELECT 'RecordLabel', id, name " +
                        "FROM contributor " +
                        "WHERE name LIKE '%$keyword%' AND dtype = 'RecordLabel' " +
                        "UNION ALL " +
                        "SELECT 'Organization', id, name " +
                        "FROM contributor " +
                        "WHERE name LIKE '%$keyword%' AND dtype = 'Organization' " +
                        "UNION ALL " +
                        "SELECT 'Award', id, title " +
                        "FROM awards " +
                        "WHERE title LIKE '%$keyword%' " +
                        "UNION ALL " +
                        "SELECT 'Venue', id, name " +
                        "FROM venues " +
                        "WHERE name LIKE '%$keyword%'")
        return ResponseEntity(query.resultList, HttpStatus.OK)
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