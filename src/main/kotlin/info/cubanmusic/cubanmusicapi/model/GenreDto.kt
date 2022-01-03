package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class GenreDto(var id: UUID? = UUID.randomUUID(), var name: String? = null, var description: String = "") :
    Serializable
