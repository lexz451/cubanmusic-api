package info.cubanmusic.cubanmusicapi.dto

import info.cubanmusic.cubanmusicapi.model.Artist
import info.cubanmusic.cubanmusicapi.model.Group
import info.cubanmusic.cubanmusicapi.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

class GroupDTO : ArtistDTO() {
    var members: List<Long> = emptyList()
}