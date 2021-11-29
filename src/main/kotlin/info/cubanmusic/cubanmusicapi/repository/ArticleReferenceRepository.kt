package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.ArticleReference
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface ArticleReferenceRepository : JpaRepository<ArticleReference, Long>, JpaSpecificationExecutor<ArticleReference> {

    @Query("select a from ArticleReference a where a.artist.id = ?1")
    @Cacheable("articles")
    fun findByArtistId(id: Long): List<ArticleReference>

    @CacheEvict("articles", allEntries = true)
    override fun <S : ArticleReference?> save(entity: S): S

    @CacheEvict("articles", allEntries = true)
    override fun deleteById(id: Long)

}