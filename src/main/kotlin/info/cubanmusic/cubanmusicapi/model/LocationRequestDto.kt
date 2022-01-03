package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class LocationRequestDto(
    var id: UUID? = UUID.randomUUID(),
    var city: String? = null,
    var state: String? = null,
    var countryId: UUID? = null
) : Serializable {

}
