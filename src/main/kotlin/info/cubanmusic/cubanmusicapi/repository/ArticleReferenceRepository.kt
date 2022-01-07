package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.ArticleReference
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ArticleReferenceRepository : JpaRepository<ArticleReference, UUID>,
    JpaSpecificationExecutor<ArticleReference> {

    @Cacheable("articles", unless = Utils.CACHE_RESULT_EMPTY)
    //@EntityGraph("article")
    @Query("select a from ArticleReference a where a.artist.id = ?1")
    fun findByArtistId(id: UUID): List<ArticleReference>

    @Cacheable("article", unless = Utils.CACHE_RESULT_NULL)
    //@EntityGraph("article")
    @Query("select a from ArticleReference a where a.id = ?1")
    fun findByIdOrNull(id: UUID): ArticleReference?

    @Caching(
        evict = [
            CacheEvict("articles", key = "#p0.artist.id"),
            CacheEvict("article", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun delete(entity: ArticleReference)

    @Caching(
        evict = [
            CacheEvict("articles", key = "#p0.artist.id"),
        ],
        put = [
            CachePut("article", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun <S : ArticleReference?> save(entity: S): S

}