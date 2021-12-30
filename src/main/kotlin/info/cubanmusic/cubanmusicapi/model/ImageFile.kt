package info.cubanmusic.cubanmusicapi.model

import javax.persistence.*

@Embeddable
open class ImageFile {
    open var filename: String? = null
    open var filetype: String? = null
    @Lob
    open var filedata: ByteArray? = null
}