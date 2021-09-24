package info.cubanmusic.cubanmusicapi.model

import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@EntityListeners(AuditingEntityListener::class)
open class BibliographicReference : AbstractAuditable<User, Long>() {
    open var source: String? = null
    open var author: String? = null
    @Temporal(TemporalType.DATE)
    open var date: Date? = null
}