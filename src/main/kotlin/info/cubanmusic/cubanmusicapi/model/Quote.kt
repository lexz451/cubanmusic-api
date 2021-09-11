package info.cubanmusic.cubanmusicapi.model

import java.time.Instant
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.Lob

@Embeddable
open class Quote {
    open var uuid: String = UUID.randomUUID().toString()
    open var source: String? = null

    @Lob
    open var quote: String? = null

    open var author: String? = null
    open var date: Date? = null
}