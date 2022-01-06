package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class OrganizationWebDto(
    var id: UUID? = UUID.randomUUID(),
    var name: String? = null,
    var description: String? = null,
    var website: String? = null
) : Serializable
