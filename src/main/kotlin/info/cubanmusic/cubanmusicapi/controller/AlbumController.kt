package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.AlbumDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Album
import info.cubanmusic.cubanmusicapi.services.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/albums")
class AlbumController {

    @Autowired
    lateinit var albumService: AlbumService
    @Autowired
    lateinit var labelService: LabelService
    @Autowired
    lateinit var artistService: ArtistService
    @Autowired
    lateinit var organizationService: OrganizationService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val albums = albumService.findAll()
        if (albums.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(albums.map { toResponse(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val album = albumService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(toResponse(album), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody request: AlbumDTO): ResponseEntity<*> {
        val album = fromRequest(Album(), request)
        albumService.save(album)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: AlbumDTO): ResponseEntity<*> {
        var album = albumService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        album = fromRequest(album, request)
        albumService.save(album)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> {
        albumService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        albumService.delete(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    private fun fromRequest(album: Album, request: AlbumDTO): Album {
        album.id = request.id
        album.title = request.title ?: ""
        album.description = request.description ?: ""
        album.releasedOn = Utils.parseDate(request.releasedOn)
        request.recordLabel?.let {
            album.recordLabel = labelService.findById(it)
        }
        if (request.artists.isNotEmpty()) {
            album.artists = artistService.findAllByIds(request.artists)
        }
        if (request.collaborations.isNotEmpty()) {
            album.collaborations = artistService.findAllByIds(request.collaborations)
        }
        if (request.organizations.isNotEmpty()) {
            album.organizations = organizationService.findAllByIds(request.organizations)
        }
        return album
    }

    private fun toResponse(album: Album): AlbumDTO {
        return AlbumDTO().apply {
            id = album.id
            title = album.title
            description = album.description
            releasedOn = Utils.formatDate(album.releasedOn)
            recordLabel = album.recordLabel?.id
            artists = album.artists.map { it.id!! }.toMutableList()
            collaborations = album.collaborations.map { it.id!! }.toMutableList()
            organizations = album.organizations.map { it.id!! }.toMutableList()
        }
    }
}