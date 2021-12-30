package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.AlbumDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.helper.Utils.formatDate
import info.cubanmusic.cubanmusicapi.helper.Utils.parseDate

import info.cubanmusic.cubanmusicapi.model.Album
import info.cubanmusic.cubanmusicapi.repository.AlbumRepository
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/albums")
class AlbumController {

    private val logger = LoggerFactory.getLogger(AlbumController::class.java)

    @Autowired
    private lateinit var albumRepository: AlbumRepository

    @Autowired
    private lateinit var mapper: ModelMapper

    @GetMapping("")
    @Transactional(readOnly = true)
    fun findAll(): ResponseEntity<*> {
        val albums = albumRepository.findAll().map { mapper.map(it, AlbumDTO::class.java) }
        return ResponseEntity(albums, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable id: UUID): ResponseEntity<*> {
        val album = albumRepository.findByIdOrNull(id)
            ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        val response = mapper.map(album, AlbumDTO::class.java)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody albumDTO: AlbumDTO): ResponseEntity<*> {
        var album = mapper.map(albumDTO, Album::class.java)
        album = albumRepository.save(album)
        return ResponseEntity(album, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody albumDTO: AlbumDTO): ResponseEntity<*> {
        val album = mapper.map(albumDTO, Album::class.java)
        albumRepository.save(album)
        return ResponseEntity(album, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<*> {
        albumRepository.deleteById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}