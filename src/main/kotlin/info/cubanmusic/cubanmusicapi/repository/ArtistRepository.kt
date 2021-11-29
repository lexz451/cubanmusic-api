package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Artist
import info.cubanmusic.cubanmusicapi.model.Instrument
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface ArtistRepository : JpaRepository<Artist, Long>, JpaSpecificationExecutor<Artist> {

    @Cacheable("artists")
    @Query("SELECT * FROM contributor WHERE dtype = 'Person' OR dtype = 'Group'", nativeQuery = true)
    override fun findAll(): List<Artist>

    @CacheEvict(cacheNames = ["artists", "albums"], allEntries = true)
    override fun <S : Artist?> save(entity: S): S

    @CacheEvict(cacheNames = ["artists", "albums"], allEntries = true)
    override fun deleteById(id: Long)

    @Query("select i.* from instruments i join artist_instrument ai on i.id = ai.instrument_id join contributor a on ai.artist_id = a.id where a.id = :id", nativeQuery = true)
    fun getArtistInstruments(id: Long): List<Any>
}