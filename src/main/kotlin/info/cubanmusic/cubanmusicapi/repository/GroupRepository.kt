package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Group
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface GroupRepository : JpaRepository<Group, UUID>, JpaSpecificationExecutor<Group> {

    @Cacheable("groups", unless = Utils.CACHE_RESULT_EMPTY)
    override fun findAll(): MutableList<Group>

    @Cacheable("group", unless = Utils.CACHE_RESULT_NULL)
    @Query("select g from Group g where g.id = ?1")
    fun findByIdOrNull(id: UUID): Group?

    @Caching(
        evict = [
            CacheEvict("groups", allEntries = true)
        ],
        put = [
            CachePut("group", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun <S : Group?> save(entity: S): S

    @Caching(
        evict = [
            CacheEvict("groups", allEntries = true),
            CacheEvict("group", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun delete(entity: Group)

}