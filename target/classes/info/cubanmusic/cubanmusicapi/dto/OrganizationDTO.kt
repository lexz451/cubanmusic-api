package info.cubanmusic.cubanmusicapi.dto

import info.cubanmusic.cubanmusicapi.model.Phone

class OrganizationDTO {
    var id: Long? = null
    var name: String? = null
    var description: String? = null
    var phone: Phone = Phone()
    var email: String? = null
    var country: Long? = null
    var website: String? = null
    var address: String? = null
}