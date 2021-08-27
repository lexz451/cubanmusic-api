package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Genre
import info.cubanmusic.cubanmusicapi.repository.GenreRepository
import info.cubanmusic.cubanmusicapi.repository.OrganizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service("genreService")
class GenreService {

    @Autowired
    lateinit var genreRepository: GenreRepository

    fun findAll():  MutableList<Genre> = genreRepository.findAll()

    fun findById(id: Long): Genre? = genreRepository.findByIdOrNull(id)

    fun findAllByIds(ids: List<Long>): MutableList<Genre> = genreRepository.findAllById(ids)

    fun save(genre: Genre) = genreRepository.save(genre)
}