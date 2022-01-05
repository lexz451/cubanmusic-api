package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Venue
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VenueRepository : JpaRepository<Venue, UUID>, JpaSpecificationExecutor<Venue> {

    @Cacheable("venues", unless = Utils.CACHE_RESULT_EMPTY)
    override fun findAll(): MutableList<Venue>

    @Cacheable("venue", unless = Utils.CACHE_RESULT_NULL)
    @Query("select v from Venue v where v.id = ?1")
    fun findByIdOrNull(id: UUID): Venue?

    @Caching(
        evict = [
            CacheEvict("venues", allEntries = true),
            CacheEvict("venue", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun delete(entity: Venue)

    @Caching(
        evict = [
            CacheEvict("venues", allEntries = true)
        ],
        put = [
            CachePut("venue", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun <S : Venue?> save(entity: S): S
}