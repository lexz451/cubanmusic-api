package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.LocationDTO
import info.cubanmusic.cubanmusicapi.model.Location
import info.cubanmusic.cubanmusicapi.repository.CountryRepository
import info.cubanmusic.cubanmusicapi.repository.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/locations")
class LocationController {

   @Autowired
   private lateinit var locationRepository: LocationRepository
   @Autowired
   private lateinit var countryRepository: CountryRepository

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val locations = locationRepository.findAll()
        return ResponseEntity(locations.map { fromModel(it) }, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody req: LocationDTO): ResponseEntity<*> {
        var location = toModel(req, Location())
        location = locationRepository.save(location)
        return ResponseEntity(location.id, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> {
        locationRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        locationRepository.deleteById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    fun fromModel(location: Location): LocationDTO {
        return LocationDTO().apply {
            id = location.id
            city = location.city
            state = location.state
            country = location.country?.id
        }
    }

    fun toModel(locationDTO: LocationDTO, location: Location): Location {
        return location.apply {
            city = locationDTO.city
            state = locationDTO.state
            locationDTO.country?.let {
                country = countryRepository.findByIdOrNull(it)
            }
        }
    }
}