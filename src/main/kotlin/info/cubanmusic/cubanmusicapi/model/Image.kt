package info.cubanmusic.cubanmusicapi.model

import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*
import javax.persistence.*
import com.fasterxml.jackson.annotation.JsonIgnore


@Table(name = "images", indexes = [
    Index(name = "idx_image_artist_id", columnList = "artist_id")
])
@Entity
open class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    open var title: String? = null

    open var author: String? = null

    @Temporal(TemporalType.DATE)
    open var date: Date? = null

    open var description: String? = null

    open var filename: String? = null

    open var filetype: String? = null

    @ElementCollection(fetch = FetchType.LAZY)
    open var tags: MutableSet<String> = mutableSetOf()

    @Lob
    open var filedata: ByteArray = byteArrayOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    open var artist: Artist? = null
}