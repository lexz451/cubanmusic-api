package info.cubanmusic.cubanmusicapi.model

import javax.persistence.*

@Table(name = "instrument")
@Entity
open class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    open var name: String = ""

    open var description: String = ""

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "INSTRUMENT_ARTIST",
        joinColumns = [JoinColumn(name = "INSTRUMENT_id")],
        inverseJoinColumns = [JoinColumn(name = "ARTIST_id")]
    )
    open var artists: MutableList<Artist> = mutableListOf()
}