package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import java.util.*
import javax.persistence.*

@NamedEntityGraph(
    name = "person",
    attributeNodes = [
        NamedAttributeNode("jobRoles"),
        NamedAttributeNode("additionalNames"),
        NamedAttributeNode("relatedArtists"),
    ]
)
@Indexed(index = "persons_idx")
@Entity
open class Person : Artist() {

    @Temporal(TemporalType.DATE)
    open var birthDate: Date? = null

    @Temporal(TemporalType.DATE)
    open var deathDate: Date? = null

    @Enumerated
    open var gender: Gender = Gender.OTHER

    @ManyToOne
    @JoinColumn(name = "birth_place_id")
    open var birthPlace: Location? = null

    @ManyToOne
    @JoinColumn(name = "death_place_id")
    open var deathPlace: Location? = null

    @ManyToOne
    @JoinColumn(name = "residence_place_id")
    open var residencePlace: Location? = null

    @ManyToOne
    @JoinColumn(name = "job_title_id")
    open var jobTitle: JobTitle? = null

    @ElementCollection
    @CollectionTable(name = "person_job_roles", joinColumns = [JoinColumn(name = "owner_id")])
    @Column(name = "job_role")
    open var jobRoles: MutableSet<String> = mutableSetOf()

    @ManyToMany
    @JoinTable(
        name = "artist_instruments",
        joinColumns = [JoinColumn(name = "artist_id")],
        inverseJoinColumns = [JoinColumn(name = "instruments_id")]
    )
    open var instruments: MutableSet<Instrument> = mutableSetOf()

    @ManyToMany(mappedBy = "members")
    open var groups: MutableList<Group> = mutableListOf()

    @PreRemove
    fun onRemovePerson() {
        for (group in this.groups) {
            group.members.remove(this)
        }
    }

    fun setInstrumentsIds(ids: MutableSet<UUID>) {
        this.instruments = ids.map {
            Instrument().apply { id = it }
        }.toMutableSet()
    }

    fun getInstrumentsIds(): MutableSet<UUID> {
        return this.instruments.map { it.id }.toMutableSet()
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Person

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}