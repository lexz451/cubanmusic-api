package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*


@Table(name = "albums")
@Entity
open class Album  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    open var title: String? = null

    @Lob
    open var description: String? = null

    @Temporal(TemporalType.DATE)
    open var releaseDate: Date? = null

    open var copyrightYear: Int? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_label_id")
    open var recordLabel: RecordLabel? = null

    @ManyToMany(mappedBy = "albums")
    open var artists: MutableList<Artist> = mutableListOf()

    @Lob
    open var image: String? = null
}