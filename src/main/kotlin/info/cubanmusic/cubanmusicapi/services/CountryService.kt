package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Country
import info.cubanmusic.cubanmusicapi.repository.CountryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CountryService {

    @Autowired
    lateinit var countryRepository: CountryRepository

    @Cacheable("countries")
    fun findAll() = countryRepository.findAll()

    @Cacheable
    fun findById(id: Long): Country? = countryRepository.findByIdNative(id)

    fun save(country: Country): Country = countryRepository.save(country)
}