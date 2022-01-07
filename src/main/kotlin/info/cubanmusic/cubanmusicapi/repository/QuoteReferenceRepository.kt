package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.QuoteReference
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface QuoteReferenceRepository : JpaRepository<QuoteReference, UUID>, JpaSpecificationExecutor<QuoteReference> {

    @Cacheable("quotes", unless = Utils.CACHE_RESULT_EMPTY)
    //@EntityGraph("quote")
    @Query("select q from QuoteReference q where q.artist.id = ?1")
    fun findByArtistId(id: UUID): List<QuoteReference>

    @Cacheable("quote", unless = Utils.CACHE_RESULT_NULL)
    //@EntityGraph("quote")
    @Query("select q from QuoteReference q where q.id = ?1")
    fun findByIdOrNull(id: UUID): QuoteReference?

    @Caching(
        evict = [
            CacheEvict("quotes", key = "#p0.artist.id"),
            CacheEvict("quote", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun delete(entity: QuoteReference)

    @Caching(
        evict = [
            CacheEvict("quotes", key = "#p0.artist.id")
        ],
        put = [
            CachePut("quote", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun <S : QuoteReference?> save(entity: S): S
}