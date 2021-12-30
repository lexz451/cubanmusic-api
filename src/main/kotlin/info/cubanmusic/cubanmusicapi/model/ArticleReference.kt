package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.domain.AbstractAuditable
import java.util.*
import javax.persistence.*

@Embeddable
open class ArticleReference {

    open var title: String? = null

    open var url: String? = null

    open var source: String? = null

    open var author: String? = null

    @Temporal(TemporalType.DATE)
    open var date: Date? = null
}