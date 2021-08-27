package info.cubanmusic.cubanmusicapi.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import info.cubanmusic.cubanmusicapi.model.Artist
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
        val artist = artistService.findById(id);
        if  (artist.isEmpty) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(artist, HttpStatus.OK)
    }
}