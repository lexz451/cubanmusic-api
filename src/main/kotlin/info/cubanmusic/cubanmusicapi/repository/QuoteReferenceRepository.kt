package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.QuoteReference
import org.hibernate.annotations.Cache
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface QuoteReferenceRepository : JpaRepository<QuoteReference, Long>, JpaSpecificationExecutor<QuoteReference> {

    @Cacheable("quotes")
    fun findByArtist_Id(id: Long): List<QuoteReference>

}