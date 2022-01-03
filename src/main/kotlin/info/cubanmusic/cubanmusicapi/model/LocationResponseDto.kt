package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

open class LocationResponseDto(
    open var id: UUID? = UUID.randomUUID(),
    open var name: String? = null
) : Serializable
