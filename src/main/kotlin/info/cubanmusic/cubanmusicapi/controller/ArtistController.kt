package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController()
@RequestMapping("/api/v1/artists")
class ArtistController {

    @Autowired
    lateinit var artistRepository: ArtistRepository
    @Autowired
    lateinit var countryRepository: CountryRepository
    @Autowired
    lateinit var organizationRepository: OrganizationRepository
    @Autowired
    lateinit var genreRepository: GenreRepository
    @Autowired
    lateinit var schoolRepository: SchoolRepository
    @Autowired
    lateinit var awardRepository: AwardRepository
    @Autowired
    lateinit var instrumentRepository: InstrumentRepository
    @Autowired
    lateinit var albumRepository: AlbumRepository
    @Autowired
    lateinit var labelRepository: RecordLabelRepository


    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val artists = artistRepository.findAll()
        if (artists.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(artists, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<*> {
        val artist = artistRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(artist, HttpStatus.OK)
    }

    /*@GetMapping("/{id}/quotes")
    fun getQuotes(@PathVariable id: Long): ResponseEntity<*> {
        val quotes = quoteRepository.findByArtistId(id)
        @Suppress("LocalVariableName") val response = quotes.map {
            val _quote = QuoteDTO()
            _quote.id = it.id
            _quote.author = it.author
            _quote.source = it.source
            _quote.date = Utils.formatDate(it.date)
            _quote.quote = it.quote
            _quote
        }
        return ResponseEntity(response, HttpStatus.OK)
    }*/

    /*@Suppress("LocalVariableName")
    @PostMapping("/{id}/quotes")
    fun addQuote(@PathVariable id: Long, @RequestBody req: QuoteDTO): ResponseEntity<*> {
        val _quoteReference = QuoteReference().apply {
            source = req.source
            author = req.author
            quote = req.quote
            date = Utils.parseDate(req.date)
        }
        _quoteReference.artist = artistRepository.findByIdOrNull(id)
        quoteRepository.save(_quoteReference)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }*/

    /*@Suppress("LocalVariableName")
    @DeleteMapping("/quotes/{quoteId}")
    fun deleteQuote(@PathVariable quoteId: Long): ResponseEntity<*> {
        quoteRepository.deleteById(quoteId)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }*/

    /*@GetMapping("/{id}/articles")
    fun getArticles(@PathVariable id: Long): ResponseEntity<*> {
        val articles = articleRepository.findByArtistId(id)
        @Suppress("LocalVariableName") val response = articles.map {
            val _article = ArticleDTO()
            _article.id = it.id
            _article.title = it.title
            _article.url = it.url
            _article.author = it.author
            _article.source = it.source
            _article.date = Utils.formatDate(it.date)
            _article
        }
        return ResponseEntity(response, HttpStatus.OK)
    }*/

    /*@PostMapping("/{id}/articles")
    fun addArticle(@PathVariable id: Long, @RequestBody req: ArticleDTO): ResponseEntity<*> {
        val articleReference = ArticleReference().apply {
            source = req.source
            author = req.author
            title = req.title
            url = req.url
            date = Utils.parseDate(req.date)
        }
        articleReference.artist = artistRepository.findByIdOrNull(id)
        articleRepository.save(articleReference)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }*/

    /*@DeleteMapping("/articles/{articleId}")
    fun deleteArticle(@PathVariable articleId: Long): ResponseEntity<*> {
        articleRepository.deleteById(articleId)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }*/

    /*fun fromModel(artist: Artist): ArtistDTO {
        return ArtistDTO().apply {
            id = artist.id.toString()
            name = artist.name
            additionalNames = artist.additionalNames
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
            //country = artist.country?.id
            affiliation = artist.affiliation?.id.toString()
            genres = artist.genres.map { it.id.toString() }
            awards = artist.awards.map { it.id.toString() }
            instruments = artist.instruments.map { it.id.toString() }
            studyAt = artist.school?.id.toString()
            albums = artist.albums.map { it.id.toString() }
            label = artist.recordLabel?.id.toString()
        }
    }

    fun toModel(artistDTO: ArtistDTO, artist: Artist): Artist {
        return artist.apply {
            name = artistDTO.name
            additionalNames = artistDTO.additionalNames.toMutableList()
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
            nationality = artistDTO.nationality
            affiliation = organizationRepository.findById(artistDTO.affiliation.toUUID())
            if (artistDTO.genres.isNotEmpty()) {
                val ids = artistDTO.genres.map { UUID.fromString(it) }
                artist.genres = genreRepository.findAllById(ids)
            }
            if (artistDTO.awards.isNotEmpty()) {
                val ids = artistDTO.awards.map { UUID.fromString(it) }
                artist.awards = awardRepository.findAllById(ids)
            }
            /*instruments = instrumentRepository.findAllById(artistDTO.instruments)
            school = schoolRepository.findByIdOrNull(artistDTO.studyAt)
            albums = albumRepository.findAllById(artistDTO.albums);
            recordLabel = labelRepository.findByIdOrNull(artistDTO.label);*/
        }
    }*/
}