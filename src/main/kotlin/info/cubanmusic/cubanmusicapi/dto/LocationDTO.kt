package info.cubanmusic.cubanmusicapi.dto

import info.cubanmusic.cubanmusicapi.model.Location
import info.cubanmusic.cubanmusicapi.repository.CountryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

class LocationDTO {
    var id: Long? = null
    var city: String? = null
    var state: String? = null
    var country: Long? = null
}