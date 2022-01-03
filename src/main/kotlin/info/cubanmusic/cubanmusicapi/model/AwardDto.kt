package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class AwardDto(
    var id: UUID? = UUID.randomUUID(),
    var name: String? = null,
    var description: String? = null,
    var countryId: UUID? = null,
    var grantedById: UUID? = null,
    var categories: MutableList<String> = mutableListOf(),
) : Serializable
