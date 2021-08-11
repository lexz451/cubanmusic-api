package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Table(name = "artist")
@Entity
open class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    open var name: String = ""

    open var alias: String = ""
    @ElementCollection

    open var additionalNames: Set<String> = setOf()

    @OneToMany(mappedBy = "artist", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var images: MutableList<Image> = mutableListOf()

    @OneToMany(mappedBy = "artist", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var awards: MutableList<Award> = mutableListOf()

    @ManyToMany(mappedBy = "artists", cascade = [CascadeType.ALL])
    open var albums: MutableList<Album> = mutableListOf()

    @ManyToMany(mappedBy = "collaborations", cascade = [CascadeType.ALL])
    open var collaborations: MutableList<Album> = mutableListOf()
    
    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null

    open var activeSince: Date? = null

    open var activeUntil: Date? = null

    @ManyToOne
    @JoinColumn(name = "affiliation_id")
    open var affiliation: Organization? = null

    @ManyToMany(mappedBy = "artists")
    open var genres: MutableList<Genre> = mutableListOf()

    @ManyToMany(mappedBy = "artists")
    open var instruments: MutableList<Instrument> = mutableListOf()

    @ManyToOne
    @JoinColumn(name = "studied_at_id")
    open var studiedAt: Organization? = null

    @ManyToMany(mappedBy = "artists")
    open var labels: MutableList<RecordLabel> = mutableListOf()

    open var description: String = ""

    open var email: String? = null

    open var website: String? = null

    open var isniCode: String? = null

    open var spotify: String? = null

    open var appleMusic: String? = null

    open var soundCloud: String? = null

    open var deezer: String? = null

    open var youtube: String? = null

    open var instagram: String? = null

    open var viberate: String? = null

    open var facebook: String? = null

    open var twitter: String? = null

    open var tiktok: String? = null

    open var libOfCongress: String? = null

    @ElementCollection(targetClass = Quote::class)
    open var quotes: Set<Quote> = setOf()

    //@ElementCollection
    //open var articles: Set<Article> = setOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Artist

        return id != null && id == other.id
    }

    override fun hashCode(): Int = 787003919
}