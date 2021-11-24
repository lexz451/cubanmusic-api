package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Venue
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface VenueRepository : JpaRepository<Venue, Long>, JpaSpecificationExecutor<Venue> {

    @Cacheable("venues")
    @Query("SELECT * FROM venues", nativeQuery = true)
    override fun findAll(): List<Venue>

    @Query("SELECT * FROM venues WHERE id = ?1", nativeQuery = true)
    fun findByIdNative(id: Long): Venue?

    @CacheEvict("venues", allEntries = true)
    override fun <S : Venue?> save(entity: S): S

    @CacheEvict("venues", allEntries = true)
    override fun deleteById(id: Long)
}