package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
open class Group : Artist() {
    @OneToMany(mappedBy = "memberOf")
    open var members: MutableList<Person> = mutableListOf()
}