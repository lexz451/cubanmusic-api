package info.cubanmusic.cubanmusicapi.model

import info.cubanmusic.cubanmusicapi.audit.LogEntity
import info.cubanmusic.cubanmusicapi.audit.LogListener
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import java.util.*
import javax.persistence.*


@Entity
@EntityListeners(LogListener::class)
@Indexed(index = "album_idx")
open class Album : LogEntity {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    @FullTextField
    open var name: String? = null

    @FullTextField
    @Lob
    open var description: String? = null

    @Temporal(TemporalType.DATE)
    open var releaseDate: Date? = null

    open var copyrightYear: Int? = null

    @Embedded
    open var imageFile: ImageFile? = null

    @ManyToMany(mappedBy = "albums")
    open var artists: MutableList<Artist> = mutableListOf()

    @ElementCollection
    @CollectionTable(name = "album_contributors", joinColumns = [JoinColumn(name = "owner_id")])
    @Column(name = "contributor")
    open var contributors: MutableSet<String> = mutableSetOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_label_id")
    open var recordLabel: RecordLabel? = null


    @PreRemove
    fun onAlbumRemove() {
        artists.forEach {
            it.albums.remove(this)
        }
    }

    override fun entityName(): String?  = name
    override fun entityType(): String? = Album::class.simpleName
    override fun entityId(): UUID = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Album

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}