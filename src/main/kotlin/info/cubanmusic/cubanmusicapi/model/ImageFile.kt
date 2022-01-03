package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Embeddable
open class ImageFile {
    open var filename: String? = null
    open var filetype: String? = null
    @Lob
    open var filedata: ByteArray? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ImageFile
        return filename == other.filename
                && filetype == other.filetype
    }

    override fun hashCode(): Int = Objects.hash(filename, filetype)
}