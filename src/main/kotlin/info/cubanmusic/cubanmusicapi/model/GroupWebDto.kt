package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class GroupWebDto(
    var id: UUID? = UUID.randomUUID(),
    var name: String? = null,
    var imageFile: ImageFileDto? = null
) :
    Serializable
