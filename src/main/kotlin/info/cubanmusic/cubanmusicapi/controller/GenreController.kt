package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.services.GenreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/genres")
class GenreController {

    @Autowired
    lateinit var genreService: GenreService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val genres = genreService.findAll()
        if (genres.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(genres, HttpStatus.OK)
    }
}