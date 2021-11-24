package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.ImageDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Image
import info.cubanmusic.cubanmusicapi.repository.ImageRepository
import info.cubanmusic.cubanmusicapi.repository.ArtistRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.data.repository.findByIdOrNull
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import kotlin.byteArrayOf


@RestController()
@RequestMapping("/api/v1/images")
class ImagesController {

    @Autowired
    lateinit var imageRepository: ImageRepository;
    @Autowired
    lateinit var artistRepository: ArtistRepository;

    private var logger = LoggerFactory.getLogger(ImagesController::class.java)

    @GetMapping("/artist/{id}")
    fun findByArtist(@PathVariable id: Long): ResponseEntity<*> {
        val images = imageRepository.findByArtistId(id)
        return ResponseEntity(images.map { toResponse(it) }, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> {
        imageRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        imageRepository.deleteById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @PostMapping("/artist/{id}/upload")
    fun upload(
        @PathVariable("id") artistId: Long,
        @RequestParam file: MultipartFile,
        @RequestParam title: String?,
        @RequestParam author: String?,
        @RequestParam description: String?,
        @RequestParam tags: String?
    ): ResponseEntity<*> {
        return try {
            val image = Image()
            image.title = title
            image.author = author
            image.description = description
            image.date = Date.from(Instant.now())
            image.filename = file.originalFilename
            image.filetype = file.contentType
            image.filedata = Utils.compressBytes(file.bytes)
            if (!tags.isNullOrEmpty()) {
                image.tags = tags.split(',').toMutableSet()
            }
            image.artist = artistRepository.findByIdOrNull(artistId)
            val savedImage = imageRepository.save(image)
            ResponseEntity(savedImage.id, HttpStatus.OK)
        } catch (e: Exception) {
            logger.error(e.message)
            ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    fun toResponse(image: Image) = ImageDTO().apply {
        id = image.id
        title = image.title
        author = image.author
        description = image.description
        date = Utils.formatDate(image.date)
        filename = image.filename
        filetype = image.filetype
        filedata = Utils.decompressBytes(image.filedata) ?: byteArrayOf()
        tags = image.tags.toList()
    }
}