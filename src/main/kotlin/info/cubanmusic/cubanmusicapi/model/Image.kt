package info.cubanmusic.cubanmusicapi.model

import javax.persistence.*

@Table(name = "image")
@Entity
open class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "artist_id")
    open var artist: Artist? = null
}