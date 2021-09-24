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

    @Basic(fetch = FetchType.LAZY)
    @ElementCollection
    open var jobRoles: MutableSet<String> = mutableSetOf()

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
    @JoinColumn(name = "job_title_id")
    open var jobTitle: JobTitle? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_to_id")
    open var relatedTo: Person? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_of_id")
    open var memberOf: Group? = null
}