package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Country
import info.cubanmusic.cubanmusicapi.services.CountryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api/v1/countries")
class CountryController {

    @Autowired
    private lateinit var countryService: CountryService

    @GetMapping("")
    fun getCountries(): ResponseEntity<*> {
        val countries = countryService.findAll()
        if (countries.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Country>>(countries, HttpStatus.OK)
    }
}