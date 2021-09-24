package info.cubanmusic.cubanmusicapi.model

import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Table(name = "locations")
@Entity
@EntityListeners(AuditingEntityListener::class)
open class Location : AbstractAuditable<User, Long>() {
    open var city: String? = null
    open var state: String? = null

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null
}