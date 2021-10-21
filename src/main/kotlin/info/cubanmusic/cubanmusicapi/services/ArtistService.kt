package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.repository.ArtistRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ArtistService {

    @Autowired
    lateinit var artistRepository: ArtistRepository

    @Cacheable("artists")
    fun findAll() = artistRepository.findAll()
}