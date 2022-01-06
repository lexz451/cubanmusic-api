package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class PersonWebDto(
    var id: UUID? = UUID.randomUUID(),
    var name: String? = null,
    var imageFile: ImageFileDto? = null,
    var jobTitle: JobTitleDto? = null
) : Serializable
