package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Country
import info.cubanmusic.cubanmusicapi.model.CountryDto
import info.cubanmusic.cubanmusicapi.model.Log
import info.cubanmusic.cubanmusicapi.repository.CountryRepository
import info.cubanmusic.cubanmusicapi.services.AuditService
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/api/v1/countries")
class CountryController {

    @Autowired
    private lateinit var countryRepository: CountryRepository
    @Autowired
    private lateinit var mapper: ModelMapper
    @Autowired
    private lateinit var auditService: AuditService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val countries = countryRepository.findAll().map { mapper.map(it, CountryDto::class.java) }
        return ResponseEntity(countries, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody countryDTO: CountryDto): ResponseEntity<*> {
        var country = mapper.map(countryDTO, Country::class.java)
        country = countryRepository.save(country)
        auditService.logEvent(country, Log.LogType.CREATE)
        return ResponseEntity(country.id, HttpStatus.OK)
    }
}