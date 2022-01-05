package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*
import javax.persistence.*

data class LogDto(
    var id: UUID? = UUID.randomUUID(),
    var date: String? = null,
    var type: Log.LogType = Log.LogType.CREATE,
    var entityName: String? = null,
    var entityType: String? = null,
    var entityId: UUID? = null,
    var userId: Long? = null,
    var userName: String? = null,
    var userEmail: String? = null
) : Serializable
