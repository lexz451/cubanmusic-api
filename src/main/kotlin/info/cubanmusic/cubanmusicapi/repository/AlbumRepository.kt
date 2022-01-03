package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Album
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

@Suppress("FunctionName")
interface AlbumRepository : JpaRepository<Album, UUID>, JpaSpecificationExecutor<Album> {


    @Query("select a from Album a left join a.artists artists where artists.id = ?1")
    fun findByArtistId(id: UUID): List<Album>

}