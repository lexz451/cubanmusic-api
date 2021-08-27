package info.cubanmusic.cubanmusicapi.model


import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Table(name = "organization", indexes = [
    Index(name = "idx_organization_name", columnList = "name")
])
@Entity
open class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    open var name: String = ""

    open var description: String = ""

    @Embedded
    open var phone: Phone? = null

    @Column(unique = true, length = 100)
    open var email: String? = null

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null

    @Embedded
    open var point: Point? = null

    open var website: String = ""

    open var address: String = ""

    @JsonIgnore
    @ManyToMany(mappedBy = "organizations", cascade = [CascadeType.ALL])
    open var collaborations: MutableList<Album> = mutableListOf()

    @JsonIgnore
    @OneToMany(mappedBy = "affiliation", orphanRemoval = true)
    open var affiliated: MutableList<Artist> = mutableListOf()
}