package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Country
import info.cubanmusic.cubanmusicapi.services.CountryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/api/v1/countries")
class CountryController {

    @Autowired
    private lateinit var countryService: CountryService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val countries = countryService.findAll()
        if (countries.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Country>>(countries, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody country: Country): ResponseEntity<*> {
        val _country = countryService.save(country)
        return ResponseEntity(_country.id, HttpStatus.OK)
    }
}