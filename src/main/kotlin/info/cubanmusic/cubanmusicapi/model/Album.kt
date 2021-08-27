package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Table(name = "album")
@Entity
open class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    open var title: String = ""

    open var description: String = ""

    open var releasedOn: Date? = null

    @ManyToOne
    @JoinColumn(name = "record_label_id")
    open var recordLabel: RecordLabel? = null

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "ALBUM_ARTIST",
        joinColumns = [JoinColumn(name = "ALBUM_id")],
        inverseJoinColumns = [JoinColumn(name = "ARTIST_id")]
    )
    open var artists: MutableList<Artist> = mutableListOf()

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "ALBUM_ARTIST_COLLABORATIONS",
        joinColumns = [JoinColumn(name = "ALBUM_id")],
        inverseJoinColumns = [JoinColumn(name = "ARTIST_id")]
    )
    open var collaborations: MutableList<Artist> = mutableListOf()

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "ALBUM_ORGANIZATION_COLLABORATIONS",
        joinColumns = [JoinColumn(name = "ALBUM_id")],
        inverseJoinColumns = [JoinColumn(name = "ORGANIZATION_id")]
    )
    open var organizations: MutableList<Organization> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Album

        return id != null && id == other.id
    }

    override fun hashCode(): Int = 113065996

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title , description = $description , releasedOn = $releasedOn , recordLabel = $recordLabel )"
    }

}