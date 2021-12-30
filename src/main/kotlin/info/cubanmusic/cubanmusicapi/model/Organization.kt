package info.cubanmusic.cubanmusicapi.model


import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Type
import org.springframework.data.jpa.domain.AbstractAuditable
import java.util.*
import javax.persistence.*

@Entity
open class Organization {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    open var name: String? = null

    @Lob
    open var description: String? = null

    @Embedded
    open var phone: Phone? = null

    open var email: String? = null

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null

    @OneToMany(mappedBy = "grantedBy", cascade = [CascadeType.ALL])
    open var awards: MutableList<Award> = mutableListOf()

    open var website: String? = null

    open var address: String? = null
}