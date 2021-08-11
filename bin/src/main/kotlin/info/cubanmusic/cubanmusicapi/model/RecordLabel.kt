package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
open class RecordLabel : Organization() {
    @Column(nullable = false)
    open var ipiCode: String? = null
    @Column(nullable = false)
    open var isniCode: String? = null

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "RECORD_LABEL_ARTIST",
        joinColumns = [JoinColumn(name = "RECORD_LABEL_id")],
        inverseJoinColumns = [JoinColumn(name = "ARTIST_id")]
    )
    open var artists: MutableList<Artist> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as RecordLabel

        return id != null && id == other.id
    }

    override fun hashCode(): Int = 2001294109

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(name = $name , description = $description , ipi = $ipiCode , isni = $isniCode )"
    }
}