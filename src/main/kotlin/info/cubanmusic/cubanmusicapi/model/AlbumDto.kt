package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class AlbumDto(
    var id: UUID? = UUID.randomUUID(),
    var name: String? = null,
    var description: String? = null,
    var releaseDate: String? = null,
    var copyrightYear: Int? = null,
    var imageFile: ImageFileDto? = null,
    var recordLabelId: UUID? = null,
    var contributors: MutableSet<String> = mutableSetOf()
) : Serializable
