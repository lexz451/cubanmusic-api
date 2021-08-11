package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import javax.persistence.*

@Table(name = "award")
@Entity
open class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(length = 100)
    open var title: String? = null

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

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "artist_id")
    open var artist: Artist? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Award

        return id != null && id == other.id
    }

    override fun hashCode(): Int = 1963314904

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title , description = $description , image = $image , country = $country , grantedBy = $grantedBy )"
    }
}