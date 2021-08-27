package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Country
import info.cubanmusic.cubanmusicapi.repository.CountryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service("countryService")
class CountryService {

    @Autowired
    private lateinit var countryRepository: CountryRepository

    fun findAll(): List<Country> = countryRepository.findAll()

    fun findById(id: Long) = countryRepository.findByIdOrNull(id)

    fun save(country: Country) = countryRepository.save(country)

    fun saveAll(countries: List<Country>): MutableList<Country> = countryRepository.saveAll(countries)
}