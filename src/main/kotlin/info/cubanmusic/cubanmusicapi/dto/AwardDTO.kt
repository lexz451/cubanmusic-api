package info.cubanmusic.cubanmusicapi.dto

class AwardDTO {
    var id: String? = null
    var name: String? = null
    var description: String? = null
    var grantedById: String? = null
    var countryId: String? = null
    var categories: List<String> = listOf()
}