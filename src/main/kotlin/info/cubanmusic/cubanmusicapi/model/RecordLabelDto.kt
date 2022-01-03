package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class RecordLabelDto(
    var id: UUID? = UUID.randomUUID(),
    var name: String? = null,
    var phone: PhoneDto? = null,
    var email: String? = null,
    var countryId: UUID? = null,
    var website: String? = null,
    var address: String? = null,
    var description: String? = null,
    var ipiCode: String? = null,
    var isniCode: String? = null
) : Serializable
