package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import java.util.*
import javax.persistence.*

@NamedEntityGraph(
    name = "group",
    attributeNodes = [
        NamedAttributeNode("members"),
        NamedAttributeNode("additionalNames"),
        NamedAttributeNode("relatedArtists"),
    ]
)
@Indexed(index = "groups_idx")
@Entity
open class Group : Artist() {
    @ManyToMany
    @JoinTable(
        name = "group_person",
        joinColumns = [JoinColumn(name = "group_id")],
        inverseJoinColumns = [JoinColumn(name = "person_id")]
    )
    open var members: MutableSet<Person> = mutableSetOf()

    fun setMembersIds(ids: MutableSet<UUID>) {
        this.members = ids.map { Person().apply {
            id = it
        }}.toMutableSet()
    }

    fun getMembersIds(): MutableSet<UUID> {
        return this.members.map { it.id }.toMutableSet()
    }

    @PreRemove
    fun onRemoveGroup() {
        for (member in this.members) {
            member.groups.remove(this)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Group
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}