package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.CountryDTO
import info.cubanmusic.cubanmusicapi.model.Country
import info.cubanmusic.cubanmusicapi.repository.CountryRepository
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

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val countries = countryRepository.findAll().map { mapper.map(it, CountryDTO::class.java) }
        return ResponseEntity(countries, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody countryDTO: CountryDTO): ResponseEntity<*> {
        var country = mapper.map(countryDTO, Country::class.java)
        country = countryRepository.save(country)
        return ResponseEntity(country.id, HttpStatus.OK)
    }
}