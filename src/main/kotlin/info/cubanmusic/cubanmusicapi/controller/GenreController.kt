package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.services.GenreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestBody
import info.cubanmusic.cubanmusicapi.dto.GenreDTO;
import info.cubanmusic.cubanmusicapi.repository.GenreRepository;
import info.cubanmusic.cubanmusicapi.model.Genre

@RestController
@RequestMapping("/api/v1/genres")
class GenreController {

    @Autowired
    lateinit var genreRepository: GenreRepository;

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val genres = genreRepository.findAll()
        if (genres.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(genres, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody genreDTO: GenreDTO): ResponseEntity<*> {
        var genre = Genre().apply { 
            name = genreDTO.name ?: ""
            description = genreDTO.description ?: ""
        }
        genre = genreRepository.save(genre);
        return ResponseEntity(genre.id, HttpStatus.OK);
    }
}