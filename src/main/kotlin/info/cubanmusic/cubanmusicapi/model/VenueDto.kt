package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class VenueDto(
    var id: UUID? = UUID.randomUUID(),
    var name: String? = null,
    var description: String? = null,
    var venueType: Venue.VenueType? = null,
    var foundationDate: String? = null,
    var capacity: Int? = null,
    var openingHours: String? = null,
    var phone: PhoneDto? = null,
    var email: String? = null,
    var address: String? = null,
    var website: String? = null,
    var latitude: Float? = null,
    var longitude: Float? = null,
    var facebook: String? = null,
    var youtube: String? = null,
    var instagram: String? = null,
    var twitter: String? = null,
    var imageFile: ImageFileDto? = null
) : Serializable
