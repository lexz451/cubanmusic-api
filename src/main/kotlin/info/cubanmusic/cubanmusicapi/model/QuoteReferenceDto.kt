package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class QuoteReferenceDto(
    var id: UUID? = UUID.randomUUID(),
    var source: String? = null,
    var author: String? = null,
    var quote: String? = null,
    var date: String? = null
) : Serializable
