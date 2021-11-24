package info.cubanmusic.cubanmusicapi.controller


import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.security.access.annotation.Secured
import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import info.cubanmusic.cubanmusicapi.model.Artist
import info.cubanmusic.cubanmusicapi.repository.ArtistRepository
import info.cubanmusic.cubanmusicapi.repository.ImageRepository
import info.cubanmusic.cubanmusicapi.dto.public.ArtistDTO
import info.cubanmusic.cubanmusicapi.dto.public.ImageDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import kotlin.collections.emptyList

@RestController
@RequestMapping("/api/v1/public")
class WebController {

    @Autowired
    lateinit var artistRepository: ArtistRepository;
    @Autowired
    lateinit var imagesRepository: ImageRepository;

    @GetMapping("/artists")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    fun getArtists(): ResponseEntity<*> {
        val artists = this.artistRepository.findAll();        
        return ResponseEntity(artists.map(mapArtist()), HttpStatus.OK);
    }

    fun mapArtist() = { artist: Artist ->  
        ArtistDTO().apply { 
            id = artist.id
            name = artist.name
            val artistImages = imagesRepository.findByArtistId(artist.id!!)
            if (!artistImages.isNullOrEmpty()) {
                image = ImageDTO().apply { 
                    data = Utils.decompressBytes(artistImages.first().filedata)
                    type = artistImages.first().filetype
                }
            }
        }
    }
}