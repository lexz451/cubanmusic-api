package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.VenueDTO
import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Phone
import info.cubanmusic.cubanmusicapi.model.Venue
import info.cubanmusic.cubanmusicapi.model.VenueTypes
import info.cubanmusic.cubanmusicapi.services.VenuesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/venues")
class VenueController {

    @Autowired
    lateinit var venuesService: VenuesService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val venues = venuesService.findAll()
        if (venues.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(venues, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val venue = venuesService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(toResponse(venue), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody request: VenueDTO): ResponseEntity<*> {
        val venue = fromRequest(Venue(), request)
        venuesService.save(venue)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: VenueDTO): ResponseEntity<*> {
        var venue = venuesService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        venue = fromRequest(venue, request)
        venuesService.save(venue)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    private fun fromRequest(venue: Venue, request: VenueDTO): Venue {
        venue.id = request.id
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
        }
    }
}