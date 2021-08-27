package info.cubanmusic.cubanmusicapi.dto

import info.cubanmusic.cubanmusicapi.model.Gender
import info.cubanmusic.cubanmusicapi.model.Image
import info.cubanmusic.cubanmusicapi.model.Location
import info.cubanmusic.cubanmusicapi.model.Quote

data class ArtistDTO(
    var id: Long? = null,
    var name: String? = null,
    var alias: String? = null,
    var additionalNames: List<String> = emptyList(),
    var birthDate: String? = null,
    var deathDate: String? = null,
    var birthPlace: LocationDTO? = null,
    var deathPlace: LocationDTO? = null,
    var residencePlace: LocationDTO? = null,
    var gender: String = Gender.OTHER.name,
    var jobTitle: Long? = null,
    var jobRoles: List<String> = emptyList(),
    var relatedTo: Long? = null,
    var awards: List<Long> = emptyList(),
    var albums: List<Long> = emptyList(),
    var collaborations: List<Long> = emptyList(),
    var country: Long? = null,
    var activeSince: Int? = null,
    var activeUntil: Int? = null,
    var affiliation: Long? = null,
    var genres: List<Long> = emptyList(),
    var instruments: List<Long> = emptyList(),
    var studyAt: Long? = null,
    var labels: List<Long> = emptyList(),
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
    var quotes: List<Quote> = emptyList(),
    var members: List<Long> = emptyList(),
    var images: List<Image> = emptyList()
)