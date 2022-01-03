package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ImageRepository : JpaRepository<Image, UUID>, JpaSpecificationExecutor<Image> {


    @Query("select i from Image i where i.artist.id = ?1")
    fun findByArtistId(id: UUID): List<Image>

}