package info.cubanmusic.cubanmusicapi.model

import org.hibernate.annotations.Type
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
open class Location {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    open var city: String? = null

    open var state: String? = null

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null

    fun toSingleString(): String {
        val fields = listOf(city, state, country?.name)
        return fields.filterNotNull().joinToString(",")
    }
}