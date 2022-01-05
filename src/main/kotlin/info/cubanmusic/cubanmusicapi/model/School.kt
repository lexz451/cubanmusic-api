package info.cubanmusic.cubanmusicapi.model

import info.cubanmusic.cubanmusicapi.helper.Auditable
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Lob

@Entity
open class School : Auditable {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    open var name: String? = null

    @Lob
    open var description: String? = null

    override fun entityId(): UUID?  = id

    override fun entityType(): String? = School::class.qualifiedName

    override fun entityName(): String? = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as School
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}