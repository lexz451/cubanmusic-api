package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Location
import info.cubanmusic.cubanmusicapi.repository.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service("locationService")
class LocationService {

    @Autowired
    lateinit var locationRepository: LocationRepository

    fun findAll() = locationRepository.findAll()

    fun findById(id: Long) = locationRepository.findByIdOrNull(id)

    fun save(location: Location) = locationRepository.save(location)
}