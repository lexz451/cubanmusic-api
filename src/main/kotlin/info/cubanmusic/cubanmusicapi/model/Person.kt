package info.cubanmusic.cubanmusicapi.model

import java.util.*
import javax.persistence.*

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_of_id")
    open var memberOf: Group? = null

    @OneToMany
    @JoinColumn(name = "person_id")
    open var relatedArtists: MutableList<Artist> = mutableListOf()

    @ElementCollection
    @CollectionTable(name = "job_roles", joinColumns = [JoinColumn(name = "person_id")])
    @Column(name = "job_role")
    open var jobRoles: MutableList<String> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_title_id")
    open var jobTitle: JobTitle? = null
}