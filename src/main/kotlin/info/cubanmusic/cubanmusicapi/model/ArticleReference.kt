package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.domain.AbstractAuditable
import java.util.*
import javax.persistence.*

@Table(name = "ArticleReference", indexes = [
    Index(name = "idx_articlereference_id_unq", columnList = "id, title", unique = true)
])
@Entity
open class ArticleReference : AbstractAuditable<User,Long>() {
    open var title: String? = null
    open var url: String? = null
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    open var artist: Artist? = null
    open var source: String? = null
    open var author: String? = null
    @Temporal(TemporalType.DATE)
    open var date: Date? = null
}