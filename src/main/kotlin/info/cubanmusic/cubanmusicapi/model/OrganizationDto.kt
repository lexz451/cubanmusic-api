package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class OrganizationDto(
    var id: UUID? = UUID.randomUUID(),
    var name: String? = null,
    var description: String? = null,
    var phone: PhoneDto? = null,
    var email: String? = null,
    var countryId: UUID? = null,
    var website: String? = null,
    var address: String? = null
) : Serializable
