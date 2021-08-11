package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
open class Group : Artist() {

    @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var members: MutableList<Person> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Group

        return id != null && id == other.id
    }

    override fun hashCode(): Int = 478181614

}