package info.cubanmusic.cubanmusicapi.model

import java.time.Instant
import java.util.*
import javax.persistence.*

@Table(name = "image")
@Entity
open class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    open var title: String? = null
    open var author: String? = null
    open var date: Date? = null
    open var description: String? = null

    @ManyToOne
    @JoinColumn(name = "artist_id")
    open var artist: Artist? = null
}