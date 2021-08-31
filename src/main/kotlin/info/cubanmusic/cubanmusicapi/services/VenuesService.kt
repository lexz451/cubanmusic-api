package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Venue
import info.cubanmusic.cubanmusicapi.repository.VenueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service("venuesService")
class VenuesService {
    @Autowired
    private lateinit var venuesRepository: VenueRepository


    fun findAll(): MutableList<Venue> = venuesRepository.findAll()

    fun findById(id: Long) = venuesRepository.findByIdOrNull(id)

    fun save(venue: Venue) = venuesRepository.save(venue)

    fun delete(id: Long) = venuesRepository.deleteById(id)
}