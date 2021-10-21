package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Artist
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface ArtistRepository : JpaRepository<Artist, Long>, JpaSpecificationExecutor<Artist> {

    @Cacheable("artists")
    @Query("SELECT * FROM contributor", nativeQuery = true)
    override fun findAll(): List<Artist>

}