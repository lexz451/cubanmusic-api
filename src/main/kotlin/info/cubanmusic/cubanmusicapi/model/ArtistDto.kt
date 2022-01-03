package info.cubanmusic.cubanmusicapi.model

import java.io.Serializable
import java.util.*

data class ArtistDto(
    var id: UUID? = null,
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
    var schoolId: UUID? = null,
    var recordLabelId: UUID? = null,
    var organizationId: UUID? = null,
    var additionalNames: MutableSet<String> = mutableSetOf(),
    var relatedArtists: MutableSet<String> = mutableSetOf(),
    var albumsIds: MutableSet<UUID> = mutableSetOf(),
    var awardsIds: MutableSet<UUID> = mutableSetOf(),
    var imageFile: ImageFileDto? = null,
) : Serializable
