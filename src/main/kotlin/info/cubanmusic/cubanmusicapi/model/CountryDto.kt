package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class CountryDto(
    var id: UUID? = UUID.randomUUID(),
    var name: String? = null,
    var iso2Code: String? = null,
    var phoneCode: String? = null
) : Serializable
