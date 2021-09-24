package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Table(name = "albums")
@Entity
@EntityListeners(AuditingEntityListener::class)
open class Album : AbstractAuditable<User, Long>() {

    open var title: String? = null

    @Lob
    open var description: String? = null

    @Temporal(TemporalType.DATE)
    open var releaseDate: Date? = null

    open var copyrightYear: Int? = null

    @ManyToOne
    @JoinColumn(name = "record_label_id")
    open var recordLabel: RecordLabel? = null

    @ManyToMany(mappedBy = "albums")
    open var artists: MutableList<Artist> = mutableListOf()
}