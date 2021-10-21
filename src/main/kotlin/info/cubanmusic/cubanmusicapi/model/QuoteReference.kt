package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.domain.AbstractAuditable
import java.util.*
import javax.persistence.*

@Entity
open class QuoteReference : AbstractAuditable<User, Long>() {
    @Lob
    open var quote: String? = null
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    open var artist: Artist? = null
    open var source: String? = null
    open var author: String? = null
    @Temporal(TemporalType.DATE)
    open var date: Date? = null
}