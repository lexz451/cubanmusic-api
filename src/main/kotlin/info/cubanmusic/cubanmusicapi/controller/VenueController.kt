package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Log
import info.cubanmusic.cubanmusicapi.model.Venue
import info.cubanmusic.cubanmusicapi.model.VenueDto
import info.cubanmusic.cubanmusicapi.repository.VenueRepository
import info.cubanmusic.cubanmusicapi.services.AuditService
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/v1/venues")
class VenueController {

    private val logger = LoggerFactory.getLogger(VenueController::class.java)

    @Autowired
    private lateinit var venueRepository: VenueRepository
    @Autowired
    private lateinit var mapper: ModelMapper
    @Autowired
    private lateinit var auditService: AuditService

    @GetMapping("")
    @Transactional(readOnly = true)
    fun findAll(): ResponseEntity<*> {
        val venues = venueRepository.findAll()
        if (venues.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(venues, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable id: UUID): ResponseEntity<*> {
        val venue = venueRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        val response = mapper.map(venue, VenueDto::class.java)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody venueDTO: VenueDto): ResponseEntity<*> {
        var venue = mapper.map(venueDTO, Venue::class.java)
        venue = venueRepository.save(venue)
        auditService.logEvent(venue, Log.LogType.CREATE)
        return ResponseEntity(venue.id, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody venueDTO: VenueDto): ResponseEntity<*> {
        var venue = mapper.map(venueDTO, Venue::class.java)
        venue = venueRepository.save(venue)
        auditService.logEvent(venue, Log.LogType.UPDATE)
        return ResponseEntity(venue.id, HttpStatus.OK)
    }
    
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<*> {
        val venue = venueRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build<Any>()
        venueRepository.delete(venue)
        auditService.logEvent(venue, Log.LogType.DELETE)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }
}