package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Country
import info.cubanmusic.cubanmusicapi.model.Artist
import info.cubanmusic.cubanmusicapi.repository.ArtistRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service("artistService")
class ArtistService {

    @Autowired
    private lateinit var artistRepository: ArtistRepository

    fun findAll(): List<Artist> = artistRepository.findAll()

    fun findAllByIds(ids: List<Long>): MutableList<Artist> = artistRepository.findAllById(ids)

    fun findById(id: Long) = artistRepository.findByIdOrNull(id)

    fun save(artist: Artist) = artistRepository.save(artist)

    fun saveAll(artists: List<Artist>): MutableList<Artist> = artistRepository.saveAll(artists)
}