package info.cubanmusic.cubanmusicapi.dto.public

import info.cubanmusic.cubanmusicapi.dto.ArticleDTO
import info.cubanmusic.cubanmusicapi.dto.QuoteDTO

open class ArtistDTO {
    var id: Long? = null
    var name: String? = null
    var occupation: String? = null
    var image: ImageDTO? = null
    var roles: List<String> = emptyList()
    var bio: String? = null
    var birthDate: String? = null
    var nationality: String? = null
    var residence: String? = null
    var yearsActive: String? = null
    var instruments: List<String> = emptyList()
    var genres: List<String> = emptyList()
    var school: String? = null
    var isni: String? = null
    var ipiCode: String? = null
    var website: String? = null
    var spotify: String? = null
    var appleMusic: String? = null
    var soundCloud: String? = null
    var deezer: String? = null
    var youtube: String? = null
    var instagram: String? = null
    var viberate: String? = null
    var facebook: String? = null
    var twitter: String? = null
    var tiktok: String? = null
    var reverbNation: String? = null
    var albums: List<AlbumDTO> = emptyList()
    var quotes: List<QuoteDTO> = emptyList()
    var articles: List<ArticleDTO> = emptyList()
}