package info.cubanmusic.cubanmusicapi.model

import info.cubanmusic.cubanmusicapi.helper.Auditable
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*
import org.springframework.data.jpa.domain.AbstractAuditable
import java.util.*
import javax.persistence.*

@Entity
@Indexed(index = "artists_idx")
open class Artist : Auditable {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    @FullTextField
    open var name: String? = null

    @FullTextField
    @Lob
    open var biography: String? = null

    @FullTextField
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    open var school: School? = null

    @OneToMany(mappedBy = "artist", orphanRemoval = true)
    open var images: MutableList<Image> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_label_id")
    open var recordLabel: RecordLabel? = null

    @Embedded
    open var imageFile: ImageFile? = null

    @ManyToMany
    @JoinTable(
        name = "artist_awards",
        joinColumns = [JoinColumn(name = "artist_id")],
        inverseJoinColumns = [JoinColumn(name = "awards_id")]
    )
    open var awards: MutableSet<Award> = mutableSetOf()

    @ManyToMany
    @JoinTable(
        name = "artist_genres",
        joinColumns = [JoinColumn(name = "artist_id")],
        inverseJoinColumns = [JoinColumn(name = "genres_id")]
    )
    open var genres: MutableSet<Genre> = mutableSetOf()

    @ManyToMany
    @JoinTable(
        name = "artist_albums",
        joinColumns = [JoinColumn(name = "artist_id")],
        inverseJoinColumns = [JoinColumn(name = "albums_id")]
    )
    open var albums: MutableSet<Album> = mutableSetOf()

    @FullTextField
    @ElementCollection
    @CollectionTable(name = "artist_additional_names", joinColumns = [JoinColumn(name = "owner_id")])
    @Column(name = "additional_name")
    open var additionalNames: MutableSet<String> = mutableSetOf()

    @OneToMany(mappedBy = "artist", orphanRemoval = true)
    open var articleReferences: MutableList<ArticleReference> = mutableListOf()

    @OneToMany(mappedBy = "artist", orphanRemoval = true)
    open var quoteReferences: MutableList<QuoteReference> = mutableListOf()

    @ElementCollection
    @CollectionTable(name = "artist_related_artists", joinColumns = [JoinColumn(name = "owner_id")])
    @Column(name = "related_artist")
    open var relatedArtists: MutableSet<String> = mutableSetOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    open var organization: Organization? = null

    fun getAlbumsIds(): MutableSet<UUID> {
        return this.albums.map { it.id }.toMutableSet()
    }

    fun setAlbumsIds(ids: MutableSet<UUID>) {
        this.albums = ids.map { Album().apply {
            id = it
        }}.toMutableSet()
    }

    fun getGenresIds(): MutableSet<UUID> {
        return this.genres.map { it.id }.toMutableSet()
    }

    fun setGenresIds(ids: MutableSet<UUID>) {
        this.genres = ids.map { Genre().apply {
            id = it
        } }.toMutableSet()
    }

    fun getAwardsIds(): MutableSet<UUID> {
        return this.awards.map { it.id }.toMutableSet()
    }

    fun setAwardsIds(ids: MutableSet<UUID>) {
        this.awards = ids.map { Award().apply {
            id = it
        } }.toMutableSet()
    }

    override fun entityId(): UUID? = id

    override fun entityType(): String? = Artist::class.qualifiedName

    override fun entityName(): String? = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Artist
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}