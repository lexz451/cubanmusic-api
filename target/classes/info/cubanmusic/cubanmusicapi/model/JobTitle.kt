package info.cubanmusic.cubanmusicapi.model

import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Table(name = "job_titles", indexes = [
    Index(name = "idx_jobtitle_id_name_unq", columnList = "id, name", unique = true)
])
@Entity
@EntityListeners(AuditingEntityListener::class)
open class JobTitle : AbstractAuditable<User, Long>() {

    open var name: String? = null

    @Lob
    open var description: String = ""
}