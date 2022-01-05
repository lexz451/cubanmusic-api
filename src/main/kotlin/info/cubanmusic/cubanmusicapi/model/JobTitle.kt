package info.cubanmusic.cubanmusicapi.model

import info.cubanmusic.cubanmusicapi.helper.Auditable
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
open class JobTitle: Auditable {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    open var name: String? = null

    @Lob
    open var description: String = ""

    @OneToMany
    @JoinColumn
    open var persons: MutableList<Person> = mutableListOf()

    override fun entityId(): UUID? = id

    override fun entityType(): String? = Instrument::class.qualifiedName

    override fun entityName(): String? = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as JobTitle
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}