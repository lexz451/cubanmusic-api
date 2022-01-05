package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Location
import info.cubanmusic.cubanmusicapi.model.LocationRequestDto
import info.cubanmusic.cubanmusicapi.model.LocationResponseDto
import info.cubanmusic.cubanmusicapi.model.Log
import info.cubanmusic.cubanmusicapi.repository.LocationRepository
import info.cubanmusic.cubanmusicapi.services.AuditService
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/locations")
class LocationController {

   @Autowired
   private lateinit var locationRepository: LocationRepository
   @Autowired
   private lateinit var mapper: ModelMapper
   @Autowired
   private lateinit var auditService: AuditService

    @GetMapping("")
    @Transactional(readOnly = true)
    fun findAll(): ResponseEntity<Any> {
        val locations = locationRepository.findAll().map { mapper.map(it, LocationResponseDto::class.java) }
        return ResponseEntity.ok(locations)
    }

    @PostMapping("/new")
    fun create(@RequestBody locationRequestDTO: LocationRequestDto): ResponseEntity<Any> {
        var location = mapper.map(locationRequestDTO, Location::class.java)
        location = locationRepository.save(location)
        auditService.logEvent(location, Log.LogType.CREATE)
        return ResponseEntity.ok(location.id)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Any> {
        if (!locationRepository.existsById(id)) return ResponseEntity.notFound().build()
        locationRepository.deleteById(id)
        return ResponseEntity.ok().build()
    }
}