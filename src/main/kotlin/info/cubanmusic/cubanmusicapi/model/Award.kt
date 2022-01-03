package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.springframework.data.jpa.domain.AbstractAuditable
import java.util.*
import javax.persistence.*

@Entity
open class Award {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    open var name: String? = null

    @Lob
    open var description: String? = null

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null

    @ManyToOne
    @JoinColumn(name = "granted_by_id")
    open var grantedBy: Organization? = null

    @ElementCollection
    @CollectionTable(name = "award_categories", joinColumns = [JoinColumn(name = "award_id")])
    @Column(name = "category")
    open var categories: MutableList<String> = mutableListOf()

    @ManyToMany(mappedBy = "awards")
    open var artists: MutableList<Artist> = mutableListOf()

    @PreRemove
    fun onAwardRemove() {
        this.artists.forEach {
            it.awards.remove(this)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Award
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}