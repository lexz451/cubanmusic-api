package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.ArticleDTO
import info.cubanmusic.cubanmusicapi.dto.QuoteDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Article
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import info.cubanmusic.cubanmusicapi.model.Artist
import info.cubanmusic.cubanmusicapi.model.Quote
import info.cubanmusic.cubanmusicapi.services.ArtistService
import org.springframework.web.bind.annotation.*


@RestController()
@RequestMapping("/api/v1/artists")
class ArtistController {

    @Autowired
    lateinit var artistService: ArtistService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val artists = artistService.findAll();
        if (artists.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Artist>>(artists, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable() id: Long): ResponseEntity<*> {
        val artist = artistService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(artist, HttpStatus.OK)
    }

    @Suppress("LocalVariableName")
    @PostMapping("/{id}/quote")
    fun addQuote(@PathVariable id: Long, @RequestBody req: QuoteDTO): ResponseEntity<*> {
        val artist = artistService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        val _quote = Quote().apply {
            source = req.source
            author = req.author
            quote = req.quote
            date = Utils.parseDate(req.date)
        }
        artist.quotes.add(_quote)
        artistService.save(artist)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @Suppress("LocalVariableName")
    @DeleteMapping("/{id}/quote/{uuid}")
    fun deleteQuote(@PathVariable id: Long, @PathVariable uuid: String): ResponseEntity<*> {
        val artist = artistService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        artist.quotes = artist.quotes.filterNot { it.uuid == uuid }.toMutableSet()
        artistService.save(artist)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @PostMapping("/{id}/article")
    fun addArticle(@PathVariable id: Long, @RequestBody req: ArticleDTO): ResponseEntity<*> {
        val artist = artistService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        val article = Article().apply {
            source = req.source
            author = req.author
            title = req.title
            date = Utils.parseDate(req.date)
        }
        artist.relatedArticles.add(article)
        artistService.save(artist)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @DeleteMapping("/{id}/article/{uuid}")
    fun deleteArticle(@PathVariable id: Long, @PathVariable uuid: String): ResponseEntity<*> {
        val artist = artistService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        artist.relatedArticles = artist.relatedArticles.filterNot { it.uuid == uuid }.toMutableSet()
        artistService.save(artist)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }
}