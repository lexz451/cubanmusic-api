package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class ImageDto(
    var id: UUID? = UUID.randomUUID(),
    var title: String? = null,
    var author: String? = null,
    var date: String? = null,
    var description: String? = null,
    var imageFile: ImageFileDto? = null,
    var tags: MutableList<String>? = mutableListOf()
) : Serializable
