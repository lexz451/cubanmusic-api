package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.domain.AbstractAuditable
import java.util.*
import javax.persistence.*

@Entity
open class ArticleReference : BibliographicReference() {
    open var title: String? = null
    open var url: String? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    open var artist: Artist? = null
}