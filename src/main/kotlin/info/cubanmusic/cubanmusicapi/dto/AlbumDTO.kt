package info.cubanmusic.cubanmusicapi.dto

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.helper.Utils.formatDate
import info.cubanmusic.cubanmusicapi.helper.Utils.parseDate
import info.cubanmusic.cubanmusicapi.model.Album
import info.cubanmusic.cubanmusicapi.repository.RecordLabelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.util.*

class AlbumDTO {
    var id: String? = null
    var name: String? = null
    var description: String? = null
    var releaseDate: String? = null
    var copyrightYear: Int? = null
    var recordLabelId: Long? = null
    var imageFile: ImageFileDTO? = null
    var artists: List<Long> = emptyList()
}

