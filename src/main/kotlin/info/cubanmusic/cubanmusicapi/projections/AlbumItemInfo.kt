package info.cubanmusic.cubanmusicapi.projections

import java.util.*

interface AlbumItemInfo {
    val id: Long?
    val image: String?
    val releaseDate: Date?
    val title: String?
}