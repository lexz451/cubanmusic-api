package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class JobTitleDto(var id: UUID? = UUID.randomUUID(), var name: String? = null, var description: String = "") :
    Serializable
