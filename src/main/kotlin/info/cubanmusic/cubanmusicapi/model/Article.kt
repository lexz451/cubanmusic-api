package info.cubanmusic.cubanmusicapi.model

import java.util.*
import javax.persistence.*

@Embeddable
open class Article {
    open var uuid: String = UUID.randomUUID().toString()
    open var title: String? = null
    open var author: String? = null
    open var source: String? = null
    open var date: Date? = null
}