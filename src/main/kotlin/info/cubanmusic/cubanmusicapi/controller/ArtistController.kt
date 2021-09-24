package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.AlbumDTO
import info.cubanmusic.cubanmusicapi.dto.ArticleDTO
import info.cubanmusic.cubanmusicapi.dto.ArtistDTO
import info.cubanmusic.cubanmusicapi.dto.QuoteDTO
import info.cubanmusic.cubanmusicapi.dto.ImageDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Album
import info.cubanmusic.cubanmusicapi.model.ArticleReference
import info.cubanmusic.cubanmusicapi.model.Artist
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import info.cubanmusic.cubanmusicapi.model.QuoteReference
import info.cubanmusic.cubanmusicapi.model.Image
import info.cubanmusic.cubanmusicapi.repository.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*



@RestController()
@RequestMapping("/api/v1/artists")
class ArtistController {

    @Autowired
    lateinit var artistRepository: ArtistRepository
    @Autowired
    lateinit var quoteRepository: QuoteReferenceRepository
    @Autowired
    lateinit var articleRepository: ArticleReferenceRepository
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
        val artists = artistRepository.findAll();
        if (artists.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(artists.map { fromModel(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable() id: Long): ResponseEntity<*> {
        val artist = artistRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(fromModel(artist), HttpStatus.OK)
    }

    @GetMapping("/{id}/quotes")
    fun getQuotes(@PathVariable id: Long): ResponseEntity<*> {
        val quotes = quoteRepository.findByArtist_Id(id)
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
    }

    @Suppress("LocalVariableName")
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
    }

    @Suppress("LocalVariableName")
    @DeleteMapping("/quotes/{quoteId}")
    fun deleteQuote(@PathVariable quoteId: Long): ResponseEntity<*> {
        quoteRepository.deleteById(quoteId)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @GetMapping("/{id}/articles")
    fun getArticles(@PathVariable id: Long): ResponseEntity<*> {
        val articles = articleRepository.findByArtist_Id(id)
        @Suppress("LocalVariableName") val response = articles.map {
            val _article = ArticleDTO()
            _article.id = it.id
            _article.title = it.title
            _article.author = it.author
            _article.source = it.source
            _article.date = Utils.formatDate(it.date)
            _article
        }
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/{id}/articles")
    fun addArticle(@PathVariable id: Long, @RequestBody req: ArticleDTO): ResponseEntity<*> {
        val articleReference = ArticleReference().apply {
            source = req.source
            author = req.author
            title = req.title
            date = Utils.parseDate(req.date)
        }
        articleReference.artist = artistRepository.findByIdOrNull(id)
        articleRepository.save(articleReference)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @DeleteMapping("/articles/{articleId}")
    fun deleteArticle(@PathVariable articleId: Long): ResponseEntity<*> {
        articleRepository.deleteById(articleId)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    fun fromModel(artist: Artist): ArtistDTO {
        return ArtistDTO().apply {
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
            instruments = artist.instruments.map { it.id!! }
            studyAt = artist.studyAt?.id
            albums = artist.albums.map { it.id!! }
            label = artist.recordLabel?.id;
        }
    }

    fun toModel(artistDTO: ArtistDTO, artist: Artist): Artist {
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
            nationality = artistDTO.nationality
            country = countryRepository.findByIdOrNull(artistDTO.country)
            affiliation = organizationRepository.findByIdOrNull(artistDTO.affiliation)
            genres = genreRepository.findAllById(artistDTO.genres)
            awards = awardRepository.findAllById(artistDTO.awards)
            instruments = instrumentRepository.findAllById(artistDTO.instruments)
            studyAt = schoolRepository.findByIdOrNull(artistDTO.studyAt)
            albums = albumRepository.findAllById(artistDTO.albums);
            recordLabel = labelRepository.findByIdOrNull(artistDTO.label);
        }
    }
}