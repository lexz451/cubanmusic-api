package info.cubanmusic.cubanmusicapi.model


import com.fasterxml.jackson.annotation.JsonIgnore
import info.cubanmusic.cubanmusicapi.helper.Auditable
import org.hibernate.annotations.Type
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import org.springframework.data.jpa.domain.AbstractAuditable
import java.util.*
import javax.persistence.*

@Entity
@Indexed(index = "organizations_idx")
open class Organization : Auditable {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    @FullTextField
    open var name: String? = null

    @FullTextField
    @Lob
    open var description: String? = null

    @Embedded
    open var phone: Phone? = null

    open var email: String? = null

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null

    @OneToMany(mappedBy = "grantedBy")
    open var awards: MutableList<Award> = mutableListOf()

    open var website: String? = null

    open var address: String? = null

    @OneToMany(mappedBy = "organization")
    open var artists: MutableList<Artist> = mutableListOf()

    @PreRemove
    fun onOrganizationRemove() {
        for (award in this.awards) {
            award.grantedBy = null
        }
        for (artist in this.artists) {
            artist.organization = null
        }
    }

    override fun entityId(): UUID? = id

    override fun entityType(): String? = Organization::class.qualifiedName

    override fun entityName(): String? = name
}