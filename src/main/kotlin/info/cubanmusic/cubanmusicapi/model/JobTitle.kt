package info.cubanmusic.cubanmusicapi.model

import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Table(name = "job_titles")
@Entity
@EntityListeners(AuditingEntityListener::class)
open class JobTitle : AbstractAuditable<User, Long>() {

    open var name: String? = null

    @Lob
    open var description: String = ""
}