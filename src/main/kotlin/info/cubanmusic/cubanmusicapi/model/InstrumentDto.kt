package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class InstrumentDto(var id: UUID? = UUID.randomUUID(), var name: String = "", var description: String = "") :
    Serializable
