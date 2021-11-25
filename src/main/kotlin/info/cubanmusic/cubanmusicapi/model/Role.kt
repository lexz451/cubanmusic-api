package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import javax.persistence.*

enum class Role {
    SUPER_ADMIN,
    ADMIN,
    CURATOR
}