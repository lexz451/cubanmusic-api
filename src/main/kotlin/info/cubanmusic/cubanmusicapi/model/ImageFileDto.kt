package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable

data class ImageFileDto(var filename: String? = null, var filetype: String? = null, var filedata: ByteArray? = null) :
    Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ImageFileDto
        if (filename != other.filename) return false
        if (filetype != other.filetype) return false
        if (filedata != null) {
            if (other.filedata == null) return false
            if (!filedata.contentEquals(other.filedata)) return false
        } else if (other.filedata != null) return false
        return true
    }

    override fun hashCode(): Int {
        var result = filename?.hashCode() ?: 0
        result = 31 * result + (filetype?.hashCode() ?: 0)
        result = 31 * result + (filedata?.contentHashCode() ?: 0)
        return result
    }
}
