package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import info.cubanmusic.cubanmusicapi.helper.Auditable
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField
import java.util.*
import javax.persistence.*


@Entity
@Indexed(index = "labels_idx")
open class RecordLabel : Auditable {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    @FullTextField(analyzer = "stop")
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
    @FullTextField(analyzer = "stop")
    open var description: String? = null

    @Column(unique = false)
    open var ipiCode: String? = null

    @Column(unique = false)
    open var isniCode: String? = null

    @JsonIgnore
    @OneToMany(mappedBy = "recordLabel")
    open var albums: MutableList<Album> = mutableListOf()

    @PreRemove
    fun onRecordLabelRemove() {
        for (album in this.albums) {
            album.recordLabel = null
        }
    }

    override fun entityId(): UUID? = id

    override fun entityType(): String? = RecordLabel::class.qualifiedName

    override fun entityName(): String? = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as RecordLabel
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}