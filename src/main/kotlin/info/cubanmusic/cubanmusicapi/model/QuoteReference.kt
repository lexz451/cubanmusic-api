package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import info.cubanmusic.cubanmusicapi.helper.Auditable
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.springframework.data.jpa.domain.AbstractAuditable
import java.util.*
import javax.persistence.*

@NamedEntityGraph(
    name = "quote",
    attributeNodes = [
        NamedAttributeNode("artist")
    ]
)
@Entity
open class QuoteReference : Auditable {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    open var source: String? = null

    open var author: String? = null

    @Lob
    open var quote: String? = null

    @Temporal(TemporalType.DATE)
    open var date: Date? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    open var artist: Artist? = null

    override fun entityId(): UUID? = id

    override fun entityType(): String? = QuoteReference::class.qualifiedName

    override fun entityName(): String? = "$source - $author"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as QuoteReference
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}