package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Image
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ImageRepository : JpaRepository<Image, UUID>, JpaSpecificationExecutor<Image> {


    @Cacheable("images", unless = Utils.CACHE_RESULT_EMPTY)
    @EntityGraph("image")
    @Query("select i from Image i where i.artist.id = ?1")
    fun findByArtistId(id: UUID): List<Image>

    @Cacheable("image", unless = Utils.CACHE_RESULT_NULL)
    @EntityGraph("image")
    @Query("select i from Image i where i.id = ?1")
    fun findByIdOrNull(id: UUID): Image?

    @Caching(
        evict = [
            CacheEvict("images", key = "#p0.artist.id")
        ],
        put = [
            CachePut("image", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun <S : Image?> save(entity: S): S

    @Caching(
        evict = [
            CacheEvict("images", key = "#p0.artist.id"),
            CacheEvict("image", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun delete(entity: Image)

}