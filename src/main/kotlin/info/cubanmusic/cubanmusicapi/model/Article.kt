package info.cubanmusic.cubanmusicapi.model

import javax.persistence.*

@Embeddable
open class Article {
    open var title: String? = null
    open var lead: String? = null
    open var url: String? = null
    open var source: String? = null
    open var author: String? = null
    @OneToOne
    @JoinColumn(name = "image_id")
    open var image: Image? = null
}