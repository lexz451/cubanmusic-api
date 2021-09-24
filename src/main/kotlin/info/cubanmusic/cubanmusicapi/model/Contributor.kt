package info.cubanmusic.cubanmusicapi.model

import org.hibernate.annotations.Polymorphism
import org.hibernate.annotations.PolymorphismType
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Table(name = "contributor")
@Entity
@EntityListeners(AuditingEntityListener::class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Polymorphism(type = PolymorphismType.IMPLICIT)
open class Contributor : AbstractAuditable<User, Long>() {
    open var name: String? = null
}