package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.AlbumDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Album
import info.cubanmusic.cubanmusicapi.repository.AlbumRepository
import info.cubanmusic.cubanmusicapi.repository.RecordLabelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/albums")
class AlbumController {

    @Autowired
    private lateinit var albumRepository: AlbumRepository
    @Autowired
    private lateinit var recordLabelRepository: RecordLabelRepository

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val albums = albumRepository.findAll()
        if (albums.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(albums.map { fromModel(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val album = albumRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(fromModel(album), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody request: AlbumDTO): ResponseEntity<*> {
        var album = toModel(request, Album())
        album = albumRepository.save(album)
        return ResponseEntity(album.id,HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: AlbumDTO): ResponseEntity<*> {
        var album = albumRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        album = toModel(request, album)
        albumRepository.save(album)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> {
        albumRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        albumRepository.deleteById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    fun fromModel(album: Album): AlbumDTO {
        return AlbumDTO().apply {
            id = album.id
            title = album.title
            description = album.description
            releaseDate = Utils.formatDate(album.releaseDate)
            copyrightYear = album.copyrightYear
            recordLabel = album.recordLabel?.id
        }
    }

    fun toModel(albumDTO: AlbumDTO, album: Album): Album {
        return album.apply {
            title = albumDTO.title
            description = albumDTO.description
            releaseDate = Utils.parseDate(albumDTO.releaseDate)
            copyrightYear = albumDTO.copyrightYear
            albumDTO.recordLabel?.let {
                recordLabel = recordLabelRepository.findByIdOrNull(it);
            }
        }
    }

}