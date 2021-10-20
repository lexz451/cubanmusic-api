package info.cubanmusic.cubanmusicapi.model

import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Table(name = "BibliographicReference", indexes = [
    Index(name = "idx_bibliographicreference_unq", columnList = "id, artist_id, title", unique = true)
])
@Entity
@EntityListeners(AuditingEntityListener::class)
open class BibliographicReference : AbstractAuditable<User, Long>() {
    open var source: String? = null
    open var author: String? = null
    @Temporal(TemporalType.DATE)
    open var date: Date? = null
}