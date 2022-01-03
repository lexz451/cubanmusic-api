package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class ArticleReferenceDto(
    var id: UUID? = UUID.randomUUID(),
    var title: String? = null,
    var url: String? = null,
    var source: String? = null,
    var author: String? = null,
    var date: String? = null
) : Serializable
