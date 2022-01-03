package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.QuoteReference
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface QuoteReferenceRepository : JpaRepository<QuoteReference, UUID>, JpaSpecificationExecutor<QuoteReference> {

    @Query("select q from QuoteReference q where q.artist.id = ?1")
    fun findByArtistId(id: UUID): List<QuoteReference>

}