package info.cubanmusic.cubanmusicapi.model

import org.hibernate.annotations.Type
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*


@Entity
@Indexed(index = "venue_idx")
open class Venue(

) {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    @FullTextField
    open var name: String? = null

    @FullTextField
    @Lob
    open var description: String? = null

    @Enumerated
    @Column(name = "venue_type")
    open var venueType: VenueTypes? = null

    @Temporal(TemporalType.DATE)
    open var foundationDate: Date? = null

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

    @Embedded
    open var imageFile: ImageFile? = null
}