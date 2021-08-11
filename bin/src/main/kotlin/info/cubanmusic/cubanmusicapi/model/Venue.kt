package info.cubanmusic.cubanmusicapi.model

import java.util.*
import javax.persistence.*

@Table(name = "venue")
@Entity
open class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    open var name: String? = null

    open var description: String? = null

    @Enumerated
    @Column(name = "venue_type")
    open var venueType: VenueTypes? = null

    open var foundedAt: Date? = null

    open var capacity: Int? = null

    open var openingHours: String? = null

    @Embedded
    open var phone: Phone? = null

    open var email: String? = null

    open var address: String? = null

    open var website: String? = null

    @Embedded
    open var point: Point? = null

    @OneToOne
    @JoinColumn(name = "image_id")
    open var image: Image? = null
}