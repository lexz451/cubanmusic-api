package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
open class RecordLabel {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    open var name: String? = null

    @Embedded
    open var phone: Phone? = null

    open var email: String? = null

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null

    open var website: String? = null

    open var address: String? = null

    @Lob
    open var description: String? = null

    @Column(unique = true)
    open var ipiCode: String? = null

    @Column(unique = true)
    open var isniCode: String? = null

    @OneToMany(mappedBy = "recordLabel")
    open var albums: MutableList<Album> = mutableListOf()

    @PreRemove
    fun onRecordLabelRemove() {
        for (album in this.albums) {
            album.recordLabel = null
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as RecordLabel
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}