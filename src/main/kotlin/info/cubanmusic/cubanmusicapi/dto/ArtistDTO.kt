package info.cubanmusic.cubanmusicapi.dto

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.*
import info.cubanmusic.cubanmusicapi.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

open class ArtistDTO(
        var id: Long? = null,
        var name: String? = null,
        var alias: String? = null,
        var additionalNames: List<String> = emptyList(),
        var biography: String? = null,
        var awards: List<Long> = emptyList(),
        var albums: List<Long> = emptyList(),
        var country: Long? = null,
        var nationality: String? = null,
        var activeSince: Int? = null,
        var activeUntil: Int? = null,
        var affiliation: Long? = null,
        var genres: List<Long> = emptyList(),
        var instruments: List<Long> = emptyList(),
        var studyAt: Long? = null,
        var label: Long? = null,
        var description: String? = null,
        var email: String? = null,
        var website: String? = null,
        var isniCode: String? = null,
        var spotify: String? = null,
        var appleMusic: String? = null,
        var soundCloud: String? = null,
        var deezer: String? = null,
        var youtube: String? = null,
        var instagram: String? = null,
        var viberate: String? = null,
        var facebook: String? = null,
        var twitter: String? = null,
        var tiktok: String? = null,
        var libOfCongress: String? = null,
        var images: List<Long> = emptyList()
) {

}
