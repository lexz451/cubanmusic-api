package info.cubanmusic.cubanmusicapi.model

import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Table(name = "venues", indexes = [
    Index(name = "idx_venue_id_name_unq", columnList = "id, name", unique = true)
])
@Entity
open class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    open var name: String? = null

    @Lob
    open var description: String? = null

    @Enumerated
    @Column(name = "venue_type")
    open var venueType: VenueTypes? = null

    @Temporal(TemporalType.DATE)
    open var foundedAt: Date? = null

    open var capacity: Int? = null

    open var openingHours: String? = null

    @Embedded
    open var phone: Phone? = null

    open var email: String? = null

    open var address: String? = null

    open var website: String? = null

    open var latitude: Float? = null

    open var longitude: Float? = null

    open var facebook: String? = null

    open var youtube: String? = null

    open var instagram: String? = null

    open var twitter: String? = null

    @Lob
    open var image: String? = null
}