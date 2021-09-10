package info.cubanmusic.cubanmusicapi.dto

class ImageDTO {
    var id: Long? = null
    var title: String? = null
    var author: String? = null
    var description: String? = null
    var date: String? = null
    var filename: String? = null
    var filetype: String? = null
    var filedata: ByteArray = byteArrayOf()
    var tags: List<String> = listOf()
}