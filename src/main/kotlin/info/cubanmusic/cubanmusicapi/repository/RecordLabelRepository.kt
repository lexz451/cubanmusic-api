package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.RecordLabel
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*


interface RecordLabelRepository : JpaRepository<RecordLabel, UUID>, JpaSpecificationExecutor<RecordLabel> {

    @Cacheable("labels", unless = Utils.CACHE_RESULT_EMPTY)
    @EntityGraph("record_label")
    override fun findAll(): MutableList<RecordLabel>

    @Cacheable("label", unless = Utils.CACHE_RESULT_NULL)
    @Query("select r from RecordLabel r where r.id = ?1")
    @EntityGraph("record_label")
    fun findByIdOrNull(id: UUID): RecordLabel?

    @Caching(
        evict = [
            CacheEvict("labels", allEntries = true)
        ],
        put = [
            CachePut("label", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun <S : RecordLabel?> save(entity: S): S

    @Caching(
        evict = [
            CacheEvict("labels", allEntries = true),
            CacheEvict("label", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun delete(entity: RecordLabel)
}