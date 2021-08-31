package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Album
import info.cubanmusic.cubanmusicapi.repository.AlbumRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service("albumService")
class AlbumService {

    @Autowired
    lateinit var albumRepository: AlbumRepository

    fun findAll(): MutableList<Album> = albumRepository.findAll()

    fun findAllByIds(ids: List<Long>): MutableList<Album> = albumRepository.findAllById(ids)

    fun findById(id: Long) = albumRepository.findByIdOrNull(id)

    fun save(album: Album) = albumRepository.save(album)

    fun delete(id: Long) = albumRepository.deleteById(id)
}