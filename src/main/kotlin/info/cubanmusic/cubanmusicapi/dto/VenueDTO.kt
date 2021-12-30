package info.cubanmusic.cubanmusicapi.dto

import info.cubanmusic.cubanmusicapi.model.Phone

class VenueDTO {
    var id: String? = null
    var name: String? = null
    var description: String? = null
    var venueType: String? = null
    var foundationDate: String? = null
    var capacity: Int? = null
    var openingHours: String? = null
    var phone: Phone = Phone()
    var email: String? = null
    var website: String? = null
    var address: String? = null
    var latitude: String? = null
    var longitude: String? = null
    var youtube: String? = null
    var instagram: String? = null
    var facebook: String? = null
    var twitter: String? = null
    var imageFile: ImageFileDTO? = null
}