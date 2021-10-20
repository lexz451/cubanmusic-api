package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.VenueDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Phone
import info.cubanmusic.cubanmusicapi.model.Venue
import info.cubanmusic.cubanmusicapi.model.VenueTypes
import info.cubanmusic.cubanmusicapi.repository.VenueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull


@RestController
@RequestMapping("/api/v1/venues")
class VenueController {

    private val logger = LoggerFactory.getLogger(VenueController::class.java)

    @Autowired
    lateinit var venueRepository: VenueRepository

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val venues = venueRepository.findAll()
        if (venues.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(venues.map { toResponse(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val venue = venueRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(toResponse(venue), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody request: VenueDTO): ResponseEntity<*> {
        var venue = fromRequest(Venue(), request)
        venue = venueRepository.save(venue)
        return ResponseEntity(venue.id,HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: VenueDTO): ResponseEntity<*> {
        var venue = venueRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        venue = fromRequest(venue, request)
        venueRepository.save(venue)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }
    
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> {
        venueRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        venueRepository.deleteById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    private fun fromRequest(venue: Venue, request: VenueDTO): Venue {
        venue.name = request.name
        venue.description = request.description
        venue.venueType = VenueTypes.valueOf(request.venueType ?: VenueTypes.CLUB.name)
        venue.foundedAt = Utils.parseDate(request.foundedAt)
        venue.capacity = request.capacity
        venue.openingHours = request.openingHours
        venue.phone = request.phone
        venue.email = request.email
        venue.website = request.website
        venue.address = request.address
        venue.latitude = request.latitude?.toFloatOrNull()
        venue.longitude = request.longitude?.toFloatOrNull()
        venue.youtube = request.youtube
        venue.facebook = request.facebook
        venue.instagram = request.instagram
        venue.twitter = request.twitter
        return venue
    }

    private fun toResponse(venue: Venue): VenueDTO {
        return VenueDTO().apply {
            id = venue.id
            name = venue.name
            description = venue.description
            venueType = venue.venueType?.name
            foundedAt = Utils.formatDate(venue.foundedAt)
            capacity = venue.capacity
            openingHours = venue.openingHours
            phone = venue.phone ?: Phone()
            email = venue.email
            website = venue.website
            address = venue.address
            if (venue.latitude != null) {
                latitude = venue.latitude?.toString()
            }
            if (venue.longitude != null) {
                longitude = venue.longitude?.toString()
            }
            facebook = venue.facebook
            youtube = venue.youtube
            instagram = venue.instagram
            twitter = venue.twitter
        }
    }
}