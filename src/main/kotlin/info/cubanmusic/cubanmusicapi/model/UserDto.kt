package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class UserDto(
    var id: Long? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var enabled: Boolean = false,
    var role: Role? = Role.CURATOR
) : Serializable
