package info.cubanmusic.cubanmusicapi.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import info.cubanmusic.cubanmusicapi.repository.GenreRepository;
import info.cubanmusic.cubanmusicapi.model.Genre
import info.cubanmusic.cubanmusicapi.model.GenreDto
import info.cubanmusic.cubanmusicapi.model.Log
import info.cubanmusic.cubanmusicapi.services.AuditService
import org.modelmapper.ModelMapper
import org.springframework.transaction.annotation.Transactional

@RestController
@RequestMapping("/api/v1/genres")
class GenreController {

    @Autowired
    private lateinit var genreRepository: GenreRepository
    @Autowired
    private lateinit var mapper: ModelMapper
    @Autowired
    private lateinit var auditService: AuditService

    @GetMapping("")
    @Transactional(readOnly = true)
    fun findAll(): ResponseEntity<Any> {
        val genres = genreRepository.findAll().map {
            mapper.map(it, GenreDto::class.java)
        }
        return ResponseEntity.ok(genres)
    }

    @PostMapping("/new")
    fun create(@RequestBody genreDTO: GenreDto): ResponseEntity<Any> {
        var genre = mapper.map(genreDTO, Genre::class.java)
        genre = genreRepository.save(genre)
        auditService.logEvent(genre, Log.LogType.CREATE)
        return ResponseEntity.ok(genre.id)
    }
}