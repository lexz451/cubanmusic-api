package info.cubanmusic.cubanmusicapi.model

import org.hibernate.annotations.Type
import org.springframework.data.jpa.domain.AbstractAuditable
import java.util.*
import javax.persistence.*

@Entity
open class Artist {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    open var name: String? = null

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

    @ElementCollection
    @CollectionTable(name = "additional_names", joinColumns = [JoinColumn(name = "artist_id")])
    @Column(name = "name")
    open var additionalNames: MutableList<String> = mutableListOf()

    @ElementCollection
    @CollectionTable(name = "article_references", joinColumns = [JoinColumn(name = "artist_id")])
    @AttributeOverrides(
        AttributeOverride(name = "title", column = Column(name = "article_reference_title")),
        AttributeOverride(name = "url", column = Column(name = "article_reference_url")),
        AttributeOverride(name = "source", column = Column(name = "article_reference_source")),
        AttributeOverride(name = "author", column = Column(name = "article_reference_author")),
        AttributeOverride(name = "date", column = Column(name = "article_reference_date"))
    )
    open var articleReferences: MutableList<ArticleReference> = mutableListOf()

    @ElementCollection
    @CollectionTable(name = "quote_references", joinColumns = [JoinColumn(name = "artist_id")])
    @AttributeOverrides(
        AttributeOverride(name = "quote", column = Column(name = "quote_references_quote")),
        AttributeOverride(name = "source", column = Column(name = "quote_references_source")),
        AttributeOverride(name = "author", column = Column(name = "quote_references_author")),
        AttributeOverride(name = "date", column = Column(name = "quote_references_date"))
    )
    open var quoteReferences: MutableList<QuoteReference> = mutableListOf()

    @ManyToMany
    @JoinTable(
        name = "artists_genres",
        joinColumns = [JoinColumn(name = "artist_id")],
        inverseJoinColumns = [JoinColumn(name = "genres_id")]
    )
    open var genres: MutableList<Genre> = mutableListOf()

    @ManyToMany
    @JoinTable(
        name = "artists_awards",
        joinColumns = [JoinColumn(name = "artist_id")],
        inverseJoinColumns = [JoinColumn(name = "awards_id")]
    )
    open var awards: MutableList<Award> = mutableListOf()

    @ManyToMany
    @JoinTable(
        name = "artists_instruments",
        joinColumns = [JoinColumn(name = "artist_id")],
        inverseJoinColumns = [JoinColumn(name = "instruments_id")]
    )
    open var instruments: MutableList<Instrument> = mutableListOf()

    @ManyToMany
    @JoinTable(
        name = "artists_albums",
        joinColumns = [JoinColumn(name = "artist_id")],
        inverseJoinColumns = [JoinColumn(name = "albums_id")]
    )
    open var albums: MutableList<Album> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    open var school: School? = null

    @ManyToOne
    @JoinColumn(name = "affiliation_id")
    open var affiliation: Organization? = null

    @ManyToOne
    @JoinColumn(name = "label_id")
    open var recordLabel: RecordLabel? = null
}