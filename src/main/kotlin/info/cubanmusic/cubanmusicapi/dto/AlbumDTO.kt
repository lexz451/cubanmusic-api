package info.cubanmusic.cubanmusicapi.dto

class AlbumDTO {
    var id: Long? = null
    var title: String? = null
    var description: String? = null
    var releasedOn: String? = null
    var recordLabel: Long? = null
    var artists: MutableList<Long> = mutableListOf()
    var collaborations: MutableList<Long> = mutableListOf()
    var organizations: MutableList<Long> = mutableListOf()
}