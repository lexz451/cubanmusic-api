package info.cubanmusic.cubanmusicapi.dto

import info.cubanmusic.cubanmusicapi.model.Phone

class RecordLabelDTO {
    var id: Long? = null
    var ipiCode: String? = null
    var isniCode: String? = null
    var name: String? = null
    var description: String? = null
    var phone: Phone = Phone()
    var email: String? = null
    var country: Long? = null
    var website: String? = null
    var address: String? = null
}