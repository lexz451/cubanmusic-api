package info.cubanmusic.cubanmusicapi.model

import javax.persistence.*

@Table(name = "location")
@Entity
open class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    open var address: String = ""

    open var city: String = ""

    open var state: String = ""

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null
}