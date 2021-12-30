package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.helper.Utils.compress
import info.cubanmusic.cubanmusicapi.model.ImageFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController()
@RequestMapping("/api/v1/images")
class ImagesController {


    /*@Autowired
    lateinit var imageRepository: ImageRepository;
    @Autowired
    lateinit var artistRepository: ArtistRepository;

    private var logger = LoggerFactory.getLogger(ImagesController::class.java)

    @GetMapping("/artist/{id}")
    @Transactional(readOnly = true)
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
    }*/
}