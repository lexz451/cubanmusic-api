package info.cubanmusic.cubanmusicapi.audit

import java.util.*

interface LogEntity {
    fun entityName(): String?
    fun entityType(): String?
    fun entityId(): UUID?
}