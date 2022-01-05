package info.cubanmusic.cubanmusicapi.model

import info.cubanmusic.cubanmusicapi.helper.Auditable
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
open class Location : Auditable {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    open var city: String? = null

    open var state: String? = null

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null

    open fun toSingleString(): String {
        val fields = listOf(city, state, country?.name)
        return fields.filterNotNull().joinToString(", ")
    }

    override fun entityId(): UUID? = id

    override fun entityType(): String? = Location::class.qualifiedName

    override fun entityName(): String? = toSingleString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Location
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}