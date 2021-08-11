package info.cubanmusic.cubanmusicapi.model

import java.time.Instant
import java.util.*
import javax.persistence.Embeddable
import javax.persistence.Entity

@Embeddable
open class Quote {
    open var source: String? = null
    open var quote: String? = null
    open var date: Date? = null
}