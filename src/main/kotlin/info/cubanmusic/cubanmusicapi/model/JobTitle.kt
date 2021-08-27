package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Table(name = "job_title")
@Entity
open class JobTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(length = 100)
    open var title: String? = null

    open var description: String = ""

    @JsonIgnore
    @OneToMany(mappedBy = "jobTitle", orphanRemoval = true)
    open var persons: MutableList<Person> = mutableListOf()
}