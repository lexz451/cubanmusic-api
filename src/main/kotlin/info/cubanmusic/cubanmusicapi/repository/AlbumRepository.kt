package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Album
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

@Suppress("FunctionName")
interface AlbumRepository : JpaRepository<Album, UUID>, JpaSpecificationExecutor<Album> {

    @Cacheable("albums", unless = Utils.CACHE_RESULT_EMPTY)
    @EntityGraph("album")
    override fun findAll(): MutableList<Album>

    @Cacheable("album", unless = Utils.CACHE_RESULT_NULL)
    @EntityGraph("album")
    @Query("select a from Album a where a.id = ?1")
    fun findByIdOrNull(id: UUID): Album?

    @Caching(
        evict = [
            CacheEvict("albums", allEntries = true)
        ],
        put = [
            CachePut("album", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun <S : Album?> save(entity: S): S

    @Caching(
        evict = [
            CacheEvict("albums", allEntries = true),
            CacheEvict("album", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun delete(entity: Album)
}