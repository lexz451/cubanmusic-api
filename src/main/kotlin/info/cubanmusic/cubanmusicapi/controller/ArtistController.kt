package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.*
import info.cubanmusic.cubanmusicapi.repository.*
import info.cubanmusic.cubanmusicapi.services.AuditService
import org.hibernate.Hibernate
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/v1/artists")
class ArtistController {

    @Autowired
    private lateinit var artistRepository: ArtistRepository
    @Autowired
    private lateinit var quoteReferenceRepository: QuoteReferenceRepository
    @Autowired
    private lateinit var articleReferenceRepository: ArticleReferenceRepository
    @Autowired
    private lateinit var imageRepository: ImageRepository
    @Autowired
    private lateinit var auditService: AuditService
    @Autowired
    private lateinit var mapper: ModelMapper

    @GetMapping("")
    @Transactional(readOnly = true)
    fun findAll(): ResponseEntity<Any> {
        val artists = artistRepository.findAll().map {
            mapper.map(it, ArtistDto::class.java)
        }
        return ResponseEntity.ok(artists)
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable id: UUID): ResponseEntity<Any> {
        val artist = artistRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build()
        val response = mapper.map(artist, ArtistDto::class.java)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{artistId}/quotes")
    @Transactional(readOnly = true)
    fun getQuotes(@PathVariable artistId: UUID): ResponseEntity<Any> {
        val quotes = quoteReferenceRepository.findByArtistId(artistId).map {
            mapper.map(it, QuoteReferenceDto::class.java)
        }
        return ResponseEntity.ok(quotes)
    }

    @PostMapping("/{artistId}/quotes")
    fun addQuote(@PathVariable artistId: UUID, @RequestBody quoteDTO: QuoteReferenceDto): ResponseEntity<Any> {
        val artist = artistRepository.findByIdOrNull(artistId) ?: return ResponseEntity.notFound().build()
        var quote = mapper.map(quoteDTO, QuoteReference::class.java)
        quote.artist = artist
        quote = quoteReferenceRepository.save(quote)
        auditService.logEvent(quote, Log.LogType.CREATE)
        return ResponseEntity.ok(quote.id)
    }

    @DeleteMapping("/quotes/{quoteID}")
    fun deleteQuote(@PathVariable quoteID: UUID): ResponseEntity<Any> {
        val quote = quoteReferenceRepository.findByIdOrNull(quoteID) ?: return ResponseEntity.notFound().build()
        quoteReferenceRepository.delete(quote)
        auditService.logEvent(quote, Log.LogType.DELETE)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{artistId}/articles")
    @Transactional(readOnly = true)
    fun getArticles(@PathVariable artistId: UUID): ResponseEntity<Any> {
        val articles = articleReferenceRepository.findByArtistId(artistId).map {
            mapper.map(it, ArticleReferenceDto::class.java)
        }
        return ResponseEntity.ok(articles)
    }

    @PostMapping("/{artistId}/articles")
    fun addArticle(@PathVariable artistId: UUID, @RequestBody articleDTO: ArticleReferenceDto): ResponseEntity<Any> {
        val artist = artistRepository.findByIdOrNull(artistId) ?: return ResponseEntity.notFound().build()
        var article = mapper.map(articleDTO, ArticleReference::class.java)
        article.artist = artist
        article = articleReferenceRepository.save(article)
        auditService.logEvent(article, Log.LogType.CREATE)
        return ResponseEntity.ok(article.id)
    }

    @DeleteMapping("/articles/{articleID}")
    fun deleteArticle(@PathVariable articleID: UUID): ResponseEntity<Any> {
        val article = articleReferenceRepository.findByIdOrNull(articleID) ?: return ResponseEntity.notFound().build()
        articleReferenceRepository.delete(article)
        auditService.logEvent(article, Log.LogType.DELETE)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{artistId}/images")
    @Transactional(readOnly = true)
    fun getImages(@PathVariable artistId: UUID): ResponseEntity<Any> {
        val images = imageRepository.findByArtistId(artistId).map {
            mapper.map(it, ImageDto::class.java)
        }
        return ResponseEntity.ok(images)
    }

    @PostMapping("/{artistId}/images")
    fun addImage(@PathVariable artistId: UUID, @RequestBody imageDto: ImageDto): ResponseEntity<Any> {
        val artist = artistRepository.findByIdOrNull(artistId) ?: return ResponseEntity.notFound().build()
        var image = mapper.map(imageDto, Image::class.java)
        image.artist = artist
        image = imageRepository.save(image)
        return ResponseEntity.ok(image.id)
    }

    @DeleteMapping("/images/{imageId}")
    fun deleteImage(@PathVariable imageId: UUID): ResponseEntity<Any> {
        val image = imageRepository.findByIdOrNull(imageId) ?: return ResponseEntity.notFound().build()
        imageRepository.delete(image)
        return ResponseEntity.ok().build()
    }
}