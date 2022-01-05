package info.cubanmusic.cubanmusicapi.helper

import java.util.*

interface Auditable {
    fun entityId(): UUID?
    fun entityType(): String?
    fun entityName(): String?
}