package info.cubanmusic.cubanmusicapi.model

import java.util.*
import javax.persistence.*

@Entity
open class Person() : Artist() {
    @ManyToOne
    @JoinColumn(name = "group_id")
    open var group: Group? = null

    open var birthDate: Date? = null

    open var deathDate: Date? = null

    @ManyToOne
    @JoinColumn(name = "birth_place_id")
    open var birthPlace: Location? = null

    @ManyToOne
    @JoinColumn(name = "death_place_id")
    open var deathPlace: Location? = null

    @ManyToOne
    @JoinColumn(name = "residence_place_id")
    open var residencePlace: Location? = null

    @Enumerated
    @Column(name = "gender", nullable = false)
    open var gender: Gender = Gender.OTHER

    @ManyToOne
    @JoinColumn(name = "job_title_id")
    open var jobTitle: JobTitle? = null

    @ElementCollection
    open var jobRoles: Set<String> = setOf()

    @ManyToOne
    @JoinColumn(name = "related_to_id")
    open var relatedTo: Person? = null
}