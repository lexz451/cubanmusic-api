package info.cubanmusic.cubanmusicapi.model

import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Table(name = "locations", indexes = [
    Index(name = "idx_location_id_city_unq", columnList = "id, city, state, country_id", unique = true)
])
@Entity
open class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    open var city: String? = null
    open var state: String? = null

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null
}