package info.cubanmusic.cubanmusicapi.dto

import info.cubanmusic.cubanmusicapi.model.Phone

class OrganizationDTO {
    var id: String? = null
    var name: String? = null
    var description: String? = null
    var phone: PhoneDTO? = null
    var email: String? = null
    var countryId: String? = null
    var website: String? = null
    var address: String? = null
}