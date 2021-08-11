package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Country
import info.cubanmusic.cubanmusicapi.repository.CountryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("countryService")
class CountryService {

    @Autowired
    private lateinit var countryRepository: CountryRepository

    fun saveCountry(country: Country) = countryRepository.save(country)

    fun updateCountry(country: Country) = countryRepository.save(country)

    fun findAll(): List<Country> = countryRepository.findAll()

    fun saveAll(countries: List<Country>): MutableList<Country> = countryRepository.saveAll(countries)
}