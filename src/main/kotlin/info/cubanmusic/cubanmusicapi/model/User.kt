package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.util.StdDateFormat
import info.cubanmusic.cubanmusicapi.helper.Auditable
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
open class User : Auditable {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id", nullable = false)
        open var id: Long? = null

        open var name: String? = null

        @Column(unique = true)
        open var email: String? = null

        open var password: String? = null

        open var enabled: Boolean = false

        @Enumerated
        open var role: Role? = Role.CURATOR

        @JsonIgnore
        @OneToMany(mappedBy = "user")
        open var logs: MutableList<Log> = mutableListOf()

        override fun entityId(): UUID? = null

        override fun entityType(): String? = User::class.qualifiedName

        override fun entityName(): String? = email

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
                other as User
                return id == other.id
        }

        override fun hashCode(): Int {
                var result = id.hashCode()
                result = 31 * result + (name?.hashCode() ?: 0)
                result = 31 * result + (email?.hashCode() ?: 0)
                result = 31 * result + (password?.hashCode() ?: 0)
                result = 31 * result + enabled.hashCode()
                result = 31 * result + (role?.hashCode() ?: 0)
                return result
        }
}