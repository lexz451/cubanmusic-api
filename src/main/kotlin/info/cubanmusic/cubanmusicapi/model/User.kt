package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.util.StdDateFormat
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*
import javax.persistence.*

@Table(name = "users", indexes = [
        Index(name = "idx_user_name_email_unq", columnList = "name, email", unique = true)
])
@Entity
@EntityListeners(AuditingEntityListener::class)
open class User : AbstractAuditable<User, Long>() {

        open var name: String? = null

        @Column(unique = true)
        open var email: String? = null

        open var password: String? = null

        open var enabled: Boolean = false

        @Enumerated
        open var role: Role? = Role.CURATOR

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
                other as User

                return id != null && id == other.id
        }

        override fun hashCode(): Int = 0

}