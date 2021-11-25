package info.cubanmusic.cubanmusicapi.dto

class AwardDTO {
    var id: Long? = null
    var title: String = ""
    var description: String = ""
    var grantedBy: Long? = null
    var country: Long? = null
    var categories: List<String> = listOf()
}