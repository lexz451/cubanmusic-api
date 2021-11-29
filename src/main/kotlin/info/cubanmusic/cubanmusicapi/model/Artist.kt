package info.cubanmusic.cubanmusicapi.model

import org.springframework.data.jpa.domain.AbstractAuditable
import javax.persistence.*

@Entity
open class Artist : Contributor() {

    @Basic(fetch = FetchType.EAGER)
    @ElementCollection
    open var additionalNames: MutableSet<String> = mutableSetOf()

    @Lob
    open var biography: String? = null

    open var alias: String? = null

    open var email: String? = null

    open var website: String? = null

    open var activeSince: Int? = null

    open var activeUntil: Int? = null

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

    open var reverbNation: String? = null

    open var libOfCongress: String? = null

    open var nationality: String? = null


    /*@ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null*/

    @ManyToOne
    @JoinColumn(name = "affiliation_id")
    open var affiliation: Organization? = null

    @ManyToMany()
    @JoinTable(
        name = "ARTIST_GENRE",
        joinColumns = [JoinColumn(name = "ARTIST_id")],
        inverseJoinColumns = [JoinColumn(name = "GENRE_id")]
    )
    open var genres: MutableList<Genre> = mutableListOf()

    @ManyToMany()
    @JoinTable(
            name = "ARTIST_AWARD",
            joinColumns = [JoinColumn(name = "ARTIST_id")],
            inverseJoinColumns = [JoinColumn(name = "AWARD_id")]
    )
    open var awards: MutableList<Award> = mutableListOf()

    @ManyToMany
    @JoinTable(
        name = "ARTIST_INSTRUMENT",
        joinColumns = [JoinColumn(name = "ARTIST_id")],
        inverseJoinColumns = [JoinColumn(name = "INSTRUMENT_id")]
    )
    open var instruments: MutableList<Instrument> = mutableListOf()


    @ManyToOne
    @JoinColumn(name = "school_id")
    open var studyAt: School? = null


    @OneToMany(mappedBy = "artist", orphanRemoval = true)
    open var quoteReferences: MutableList<QuoteReference> = mutableListOf()


    @OneToMany(mappedBy = "artist", orphanRemoval = true)
    open var articleReferences: MutableList<ArticleReference> = mutableListOf()

    @ManyToMany
    @JoinTable(name = "artist_albums",
            joinColumns = [JoinColumn(name = "artist_id")],
            inverseJoinColumns = [JoinColumn(name = "albums_id")])
    open var albums: MutableList<Album> = mutableListOf()

    @ManyToOne
    @JoinColumn(name = "label_id")
    open var recordLabel: RecordLabel? = null

    @OneToMany(mappedBy = "artist", orphanRemoval = true)
    open var images: MutableList<Image> = mutableListOf()
}