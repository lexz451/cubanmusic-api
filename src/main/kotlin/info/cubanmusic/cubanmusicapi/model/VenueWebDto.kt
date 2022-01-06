package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class VenueWebDto(
    var id: UUID? = UUID.randomUUID(),
    var name: String? = null,
    var venueType: Venue.VenueType? = null
) : Serializable
