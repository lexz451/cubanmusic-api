package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class PersonDetailWebDto(
    var id: UUID? = UUID.randomUUID(),
    var name: String? = null,
    var biography: String? = null,
    var alias: String? = null,
    var email: String? = null,
    var website: String? = null,
    var activeSince: Int? = null,
    var activeUntil: Int? = null,
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
    var reverbNation: String? = null,
    var libOfCongress: String? = null,
    var nationality: String? = null,
    var imageFile: ImageFileDto? = null,
    var genres: MutableSet<GenreDto> = mutableSetOf(),
    var albums: MutableSet<AlbumDto> = mutableSetOf(),
    var additionalNames: MutableSet<String> = mutableSetOf(),
    var articleReferences: MutableList<ArticleReferenceDto> = mutableListOf(),
    var quoteReferences: MutableList<QuoteReferenceDto> = mutableListOf(),
    var relatedArtists: MutableSet<String> = mutableSetOf(),
    var birthDate: String? = null,
    var deathDate: String? = null,
    var residencePlace: LocationResponseDto? = null,
    var jobTitle: JobTitleDto? = null,
    var jobRoles: MutableSet<String> = mutableSetOf(),
    var instruments: MutableSet<InstrumentDto> = mutableSetOf()
) : Serializable
