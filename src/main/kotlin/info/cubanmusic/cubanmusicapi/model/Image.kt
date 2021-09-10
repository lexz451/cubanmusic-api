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

    open var filename: String? = null
    open var filetype: String? = null

    @ElementCollection
    open var tags: List<String> = emptyList()

    @Lob
    open var filedata: ByteArray = byteArrayOf()


}