package info.cubanmusic.cubanmusicapi.model

import org.hibernate.annotations.Polymorphism
import org.hibernate.annotations.PolymorphismType
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Table(name = "contributor", indexes = [
    Index(name = "idx_contributor_id_name_unq", columnList = "id, name", unique = true)
])
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
open class Contributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null
    open var name: String? = null
}