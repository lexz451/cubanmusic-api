package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Award
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AwardRepository : JpaRepository<Award, UUID>, JpaSpecificationExecutor<Award> {

    //@EntityGraph("award")
    @Cacheable("awards", unless = Utils.CACHE_RESULT_EMPTY)
    override fun findAll(): MutableList<Award>

    @Cacheable("award", unless = Utils.CACHE_RESULT_NULL)
    //@EntityGraph("award")
    @Query("select a from Award a where a.id = ?1")
    fun findByIdOrNull(id: UUID): Award?

    @Caching(
        evict = [
            CacheEvict("awards", allEntries = true)
        ],
        put = [
            CachePut("award", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun <S : Award?> save(entity: S): S

    @Caching(
        evict = [
            CacheEvict("awards", allEntries = true),
            CacheEvict("award", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun delete(entity: Award)

}