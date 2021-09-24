package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import org.springframework.data.jpa.domain.AbstractAuditable
import javax.persistence.*

@Table(name = "awards")
@Entity
open class Award : AbstractAuditable<User, Long>() {

    @Column(length = 100)
    open var title: String? = null

    @Lob
    open var description: String = ""

    @OneToOne
    @JoinColumn(name = "image_id")
    open var image: Image? = null

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null

    @ManyToOne
    @JoinColumn(name = "granted_by_id")
    open var grantedBy: Organization? = null

    @ElementCollection
    open var categories: Set<String> = setOf()

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "ARTIST_AWARD",
        joinColumns = [JoinColumn(name = "AWARD_id")],
        inverseJoinColumns = [JoinColumn(name = "ARTIST_id")]
    )
    open var artists: MutableList<Artist> = mutableListOf()
}