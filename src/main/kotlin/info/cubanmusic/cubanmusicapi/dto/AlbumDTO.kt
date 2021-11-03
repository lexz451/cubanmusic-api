package info.cubanmusic.cubanmusicapi.dto

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Album
import info.cubanmusic.cubanmusicapi.repository.RecordLabelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

class AlbumDTO {
    var id: Long? = null
    var title: String? = null
    var description: String? = null
    var releasedDate: String? = null
    var copyrightYear: Int? = null
    var recordLabel: Long? = null
    var contributor: List<Long> = emptyList()
    var artists: List<Long> = emptyList()
    var image: String? = null
}