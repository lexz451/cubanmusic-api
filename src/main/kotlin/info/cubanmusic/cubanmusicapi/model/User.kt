package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.util.StdDateFormat
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*
import javax.persistence.*

@Table(name = "user", indexes = [
        Index(name = "idx_user_name_email_unq", columnList = "name, email", unique = true)
])
@Entity
open class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        open var id: Long? = null
        
        open var name: String? = null

        @Column(unique = true)
        open var email: String? = null

        open var password: String? = null

        open var enabled: Boolean = false

        @Enumerated
        open var role: Role? = Role.CURATOR

        @Temporal(TemporalType.TIMESTAMP)
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
        @CreationTimestamp
        open var createdAt: Date = Date.from(Instant.now())

        @Temporal(TemporalType.TIMESTAMP)
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
        @UpdateTimestamp
        open var updatedAt: Date = Date.from(Instant.now())
}