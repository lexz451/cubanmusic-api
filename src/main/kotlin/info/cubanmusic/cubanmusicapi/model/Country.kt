package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import info.cubanmusic.cubanmusicapi.helper.Auditable
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*


@Entity
open class Country : Auditable {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    open var name: String? = null

    open var iso2Code: String? = null

    open var phoneCode: String? = null

    override fun entityId(): UUID? = id

    override fun entityType(): String? = Country::class.qualifiedName

    override fun entityName(): String? = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Country
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}