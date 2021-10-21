package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Artist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface ArtistRepository : JpaRepository<Artist, Long>, JpaSpecificationExecutor<Artist> {

    @Query("SELECT * FROM contributor WHERE dtype = 'Person'", nativeQuery = true)
    override fun findAll(): List<Artist>

}