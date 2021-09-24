package info.cubanmusic.cubanmusicapi.dto

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Artist
import info.cubanmusic.cubanmusicapi.model.Gender
import info.cubanmusic.cubanmusicapi.model.Person
import info.cubanmusic.cubanmusicapi.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

class PersonDTO : ArtistDTO() {
    var birthDate: String? = null
    var deathDate: String? = null
    var birthPlace: Long? = null
    var deathPlace: Long? = null
    var residencePlace: Long? = null
    var gender: String = Gender.OTHER.name
    var jobTitle: Long? = null
    var jobRoles: List<String> = emptyList()
    var relatedTo: Long? = null
}