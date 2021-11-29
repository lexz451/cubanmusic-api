package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.QuoteReference
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface QuoteReferenceRepository : JpaRepository<QuoteReference, Long>, JpaSpecificationExecutor<QuoteReference> {

    @Query("select q from QuoteReference q where q.artist.id = ?1")
    @Cacheable("quotes")
    fun findByArtistId(id: Long): List<QuoteReference>

    @CacheEvict("quotes", allEntries = true)
    override fun <S : QuoteReference?> save(entity: S): S

    @CacheEvict("quotes", allEntries = true)
    override fun deleteById(id: Long)

}