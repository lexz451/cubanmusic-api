package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Venue
import info.cubanmusic.cubanmusicapi.repository.VenueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class VenueService {

    @Autowired
    lateinit var venueRepository: VenueRepository
    
    @Cacheable("venues")
    fun findAll() = venueRepository.findAll()

    fun findById(id: Long) = venueRepository.findByIdNative(id)

    fun save(venue: Venue): Venue = venueRepository.save(venue)

    fun deleteById(id: Long)  = venueRepository.deleteById(id)
}